package es.aramirez.rxribbon;

import es.aramirez.rxribbon.sync.JustItemRepository;
import es.aramirez.rxribbon.sync.JustLocationRepository;
import es.aramirez.rxribbon.sync.JustUserRepository;

public class Main {

  private Main() {}

  public static void main(String[] args) {
    new Service(
      new JustUserRepository(),
      new JustLocationRepository(),
      new JustItemRepository()
    )
      .execute(new ServiceRequest("Person", 42.2, 15.5, 42))
      .doOnNext(System.out::println)
      .subscribe()
    ;
  }
}
