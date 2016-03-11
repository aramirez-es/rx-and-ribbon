package es.aramirez.rxribbon;

import es.aramirez.rxribbon.sync.JustItemRepository;
import es.aramirez.rxribbon.sync.JustLocationRepository;
import es.aramirez.rxribbon.sync.JustUserRepository;
import rx.Observable;

public class Main {

  private static UserRepository userRepository;
  private static LocationRepository locationRepository;
  private static ItemRepository itemRepository;

  private Main() {
    userRepository = new JustUserRepository();
    locationRepository = new JustLocationRepository();
    itemRepository = new JustItemRepository();
  }

  public static void main(String[] args) {

    new Main();

    Observable.zip(
      userRepository.findByName("User Name"),
      locationRepository.create(42.2, 15.5),
      itemRepository.findById(42),
      Aggregated::new
    )
      .doOnNext(System.out::println)
      .subscribe()
    ;
  }
}
