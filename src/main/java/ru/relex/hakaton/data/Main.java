package ru.relex.hakaton.data;

import java.util.List;

public class Main {

  private static final String REST_URL = "http://127.0.0.1:8080/pt-api-0.0.4-SNAPSHOT/rest";

  public static void main(String[] args) {

    SenderFacade sf = new SenderFacade(REST_URL + "/passway/entrance", "");

    PassInfo p1 = addEntrance(sf, "barcode:123");
    PassInfo p2 = addEntrance(sf, "qr:Hello,Mihael!");
    PassInfo p3 = addEntrance(sf, "mac:0023D4A33819!");
    PassInfo p4 = addEntrance(sf, "qr:Hello,Denis!");

    List<PassInfo> list = sf.getPasses();
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
