package es.aramirez.rxribbon.latency;

import es.aramirez.rxribbon.Item;
import es.aramirez.rxribbon.ItemRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DeferLatencyItemRepository implements ItemRepository {
  @Override
  public Observable<Item> findById(Integer id) {
    return Observable.defer(() -> Observable.just(new Item(id)))
      .delay((long) (new Random().nextInt(30) * 100), TimeUnit.MILLISECONDS)
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
