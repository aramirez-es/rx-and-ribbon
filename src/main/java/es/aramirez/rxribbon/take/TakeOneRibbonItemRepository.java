package es.aramirez.rxribbon.take;

import es.aramirez.rxribbon.Item;
import es.aramirez.rxribbon.ItemRepository;
import es.aramirez.rxribbon.ribbon.RibbonItemRepository;
import rx.Observable;

public class TakeOneRibbonItemRepository implements ItemRepository {

  private final RibbonItemRepository ribbon;

  public TakeOneRibbonItemRepository() {
    this.ribbon = new RibbonItemRepository();
  }

  @Override
  public Observable<Item> findById(Integer id) {
    return ribbon.findById(id).take(1);
  }
}
