package es.aramirez.rxribbon.async;

import es.aramirez.rxribbon.Item;
import es.aramirez.rxribbon.ItemRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

public class DeferItemRepository implements ItemRepository {
  @Override
  public Observable<Item> findById(Integer id) {
    return Observable.defer(() -> Observable.just(new Item(id)))
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
