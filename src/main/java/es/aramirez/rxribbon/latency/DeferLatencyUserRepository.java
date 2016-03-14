package es.aramirez.rxribbon.latency;

import es.aramirez.rxribbon.User;
import es.aramirez.rxribbon.UserRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DeferLatencyUserRepository implements UserRepository{
  @Override
  public Observable<User> findByName(String userName) {
    return Observable.defer(() -> Observable.just(new User(userName)))
      .delay((long) (new Random().nextInt(30) * 100), TimeUnit.MILLISECONDS)
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
