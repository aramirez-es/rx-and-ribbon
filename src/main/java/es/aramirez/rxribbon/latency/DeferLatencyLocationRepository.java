package es.aramirez.rxribbon.latency;

import es.aramirez.rxribbon.Location;
import es.aramirez.rxribbon.LocationRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Random;

public class DeferLatencyLocationRepository implements LocationRepository {
  @Override
  public Observable<Location> create(Double latitude, Double longitude) {
    return Observable.defer(() -> {
      try {
        Thread.sleep((long) (new Random().nextInt(5) * 1000));
      } catch (InterruptedException e) { }
        return Observable.just(new Location(latitude, longitude));
      })
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
