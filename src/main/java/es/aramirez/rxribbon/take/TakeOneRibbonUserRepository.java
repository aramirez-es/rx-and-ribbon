package es.aramirez.rxribbon.take;

import es.aramirez.rxribbon.User;
import es.aramirez.rxribbon.UserRepository;
import es.aramirez.rxribbon.ribbon.RibbonUserRepository;
import rx.Observable;

public class TakeOneRibbonUserRepository implements UserRepository {

  private final RibbonUserRepository ribbonRepo;

  public TakeOneRibbonUserRepository() {
    this.ribbonRepo = new RibbonUserRepository();
  }

  @Override
  public Observable<User> findByName(String userName) {
    return ribbonRepo.findByName(userName).take(1);
  }
}
