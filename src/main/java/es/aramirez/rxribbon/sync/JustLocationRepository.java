package es.aramirez.rxribbon.sync;

import es.aramirez.rxribbon.Location;
import es.aramirez.rxribbon.LocationRepository;
import rx.Observable;

public class JustLocationRepository implements LocationRepository {
  @Override
  public Observable<Location> create(Double latitude, Double longitude) {
    return Observable.just(new Location(latitude, longitude));
  }
}
