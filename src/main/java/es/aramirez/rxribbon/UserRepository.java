package es.aramirez.rxribbon;

import rx.Observable;

public interface UserRepository {
  Observable<User> findByName(String userName);
}
