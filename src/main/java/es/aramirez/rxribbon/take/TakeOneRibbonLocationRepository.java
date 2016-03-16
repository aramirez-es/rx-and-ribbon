package es.aramirez.rxribbon.take;

import es.aramirez.rxribbon.Location;
import es.aramirez.rxribbon.LocationRepository;
import es.aramirez.rxribbon.ribbon.RibbonLocationRepository;
import rx.Observable;

public class TakeOneRibbonLocationRepository implements LocationRepository {

  private final RibbonLocationRepository ribbonRepo;

  public TakeOneRibbonLocationRepository() {
    this.ribbonRepo = new RibbonLocationRepository();
  }

  @Override
  public Observable<Location> create(Double latitude, Double longitude) {
    return ribbonRepo.create(latitude, longitude).take(1);
  }
}
