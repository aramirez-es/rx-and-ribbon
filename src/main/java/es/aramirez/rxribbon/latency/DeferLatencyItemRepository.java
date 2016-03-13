package es.aramirez.rxribbon.latency;

import es.aramirez.rxribbon.Item;
import es.aramirez.rxribbon.ItemRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Random;

public class DeferLatencyItemRepository implements ItemRepository {
  @Override
  public Observable<Item> findById(Integer id) {
    return Observable.defer(() -> {
        try {
          Thread.sleep((long) (new Random().nextInt(5) * 1000));
        } catch (InterruptedException e) { }
        return Observable.just(new Item(id));
      })
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
