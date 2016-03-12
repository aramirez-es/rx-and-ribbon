package es.aramirez.rxribbon.async;

import es.aramirez.rxribbon.Location;
import es.aramirez.rxribbon.LocationRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

public class DeferLocationRepository implements LocationRepository {
  @Override
  public Observable<Location> create(Double latitude, Double longitude) {
    return Observable.defer(() -> Observable.just(new Location(latitude, longitude)))
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
