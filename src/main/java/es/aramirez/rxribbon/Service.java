package es.aramirez.rxribbon;

import rx.Observable;

public class Service {
  private final UserRepository userRepository;
  private final LocationRepository locationRepository;
  private final ItemRepository itemRepository;

  public Service(
    UserRepository userRepository,
    LocationRepository locationRepository,
    ItemRepository itemRepository
  ) {
    this.userRepository = userRepository;
    this.locationRepository = locationRepository;
    this.itemRepository = itemRepository;
  }

  public Observable<Aggregated> execute(ServiceRequest request) {
    return Observable.zip(
      userRepository.findByName(request.getUserName()),
      locationRepository.create(request.getLatitude(), request.getLongitude()),
      itemRepository.findById(request.getItemId()),
      Aggregated::new
    );
  }
}
