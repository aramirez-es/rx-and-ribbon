package es.aramirez.rxribbon.latency;

import es.aramirez.rxribbon.User;
import es.aramirez.rxribbon.UserRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Random;

public class DeferLatencyUserRepository implements UserRepository{
  @Override
  public Observable<User> findByName(String userName) {
    return Observable.defer(() -> {
      try {
        Thread.sleep((long) (new Random().nextInt(5) * 1000));
      } catch (InterruptedException e) { }
        return Observable.just(new User(userName));
      })
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
