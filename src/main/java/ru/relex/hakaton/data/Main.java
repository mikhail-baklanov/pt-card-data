package ru.relex.hakaton.data;

import java.util.List;

import ru.relex.hakaton.data.PassInfo.UserStatus;

public class Main {

  private static final String REST_URL = "http://127.0.0.1:8080/pt-api-0.0.4-SNAPSHOT/rest";

  public static void main(String[] args) {

    SenderFacade sf = new SenderFacade(REST_URL + "/passway/entrance", "");

    PassInfo p1 = addEntrance(sf, "barcode:123");
    PassInfo p2 = addEntrance(sf, "barcode:123");
    PassInfo p3 = addEntrance(sf, "qr:Hello,Mihael!");
    PassInfo p4 = addEntrance(sf, "mac:0023D4A33819");
    PassInfo p5 = addEntrance(sf, "qr:Hello,Denis!");

    List<PassInfo> list = sf.getPasses();
    if (list.size() == 0) {
      System.out.println("Пустой список проходов");
    }
    else {
      PassInfo pi;

      // переключение в WORK
      pi = getPassByStatus(list, UserStatus.NONE);
      if (pi == null) {
        System.err.println("Приход на работу: не найдена запись со статусом NONE");
        return;
      }
      pi.setStatus(UserStatus.WORK);
      sf.applyPassInfo(pi);

      // переключение второй записи в WORK
      pi = getPassByStatus(list, UserStatus.NONE);
      if (pi == null) {
        System.err.println("Приход на работу: не найдена запись со статусом NONE");
        return;
      }
      pi.setStatus(UserStatus.WORK);
      sf.applyPassInfo(pi);

      // переключение в IGNORE
      pi = getPassByStatus(list, UserStatus.NONE);
      if (pi == null) {
        System.err.println("Отмена прохода: не найдена запись со статусом NONE");
        return;
      }
      pi.setStatus(UserStatus.IGNORE);
      sf.applyPassInfo(pi);

      // переключение в AWAY
      pi = getPassByStatus(list, UserStatus.WORK);
      if (pi == null) {
        System.err.println("Уход: не найдена запись со статусом WORK");
        return;
      }
      pi.setStatus(UserStatus.AWAY);
      sf.applyPassInfo(pi);

      // переключение в ABSENT
      pi = getPassByStatus(list, UserStatus.WORK);
      if (pi == null) {
        System.err.println("Отлучка: не найдена запись со статусом WORK");
        return;
      }
      pi.setStatus(UserStatus.ABSENT);
      sf.applyPassInfo(pi);
    }
  }

  private static PassInfo getPassByStatus(List<PassInfo> list, UserStatus status) {
    int i = 0;
    while (i < list.size() && list.get(i).getStatus() != status)
      i++;
    if (i < list.size())
      return list.get(i);
    else
      return null;
  }

  private static PassInfo addEntrance(SenderFacade sf, String id) {
    PassInfo userInfo = sf.sendId(id);
    if (userInfo != null) {
      System.out.println("Добавлен проход пользователя через вахту: " + userInfo);
    }
    else {
      System.err.println("Ошибка добавления прохода пользователя через вахту. id=" + id);
    }
    return userInfo;
  }
}
