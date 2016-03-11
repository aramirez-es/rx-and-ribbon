package es.aramirez.rxribbon.sync;

import es.aramirez.rxribbon.User;
import es.aramirez.rxribbon.UserRepository;
import rx.Observable;

public class JustUserRepository implements UserRepository {
  @Override
  public Observable<User> findByName(String userName) {
    return Observable.just(new User(userName));
  }
}
