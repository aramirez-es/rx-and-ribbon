package es.aramirez.rxribbon.latency;

import es.aramirez.rxribbon.Location;
import es.aramirez.rxribbon.LocationRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DeferLatencyLocationRepository implements LocationRepository {
  @Override
  public Observable<Location> create(Double latitude, Double longitude) {
    return Observable.defer(() -> Observable.just(new Location(latitude, longitude)))
      .delay((long) (new Random().nextInt(30) * 100), TimeUnit.MILLISECONDS)
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
