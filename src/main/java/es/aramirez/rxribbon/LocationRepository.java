package es.aramirez.rxribbon;

import rx.Observable;

public interface LocationRepository {
  Observable<Location> create(Double latitude, Double longitude);
}
