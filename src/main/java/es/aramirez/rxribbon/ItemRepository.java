package es.aramirez.rxribbon;

import rx.Observable;

public interface ItemRepository {
  Observable<Item> findById(Integer id);
}
