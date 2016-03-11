package es.aramirez.rxribbon.sync;

import es.aramirez.rxribbon.Item;
import es.aramirez.rxribbon.ItemRepository;
import rx.Observable;

public class JustItemRepository implements ItemRepository {
  @Override
  public Observable<Item> findById(Integer id) {
    return Observable.just(new Item(id));
  }
}
