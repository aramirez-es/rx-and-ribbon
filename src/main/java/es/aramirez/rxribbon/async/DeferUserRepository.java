package es.aramirez.rxribbon.async;

import es.aramirez.rxribbon.User;
import es.aramirez.rxribbon.UserRepository;
import rx.Observable;
import rx.schedulers.Schedulers;

public class DeferUserRepository implements UserRepository {
  @Override
  public Observable<User> findByName(String userName) {
    return Observable.defer(() -> Observable.just(new User(userName)))
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.computation());
  }
}
