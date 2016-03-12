package es.aramirez.rxribbon;

import com.netflix.config.DynamicPropertyFactory;
import es.aramirez.rxribbon.sync.JustItemRepository;
import es.aramirez.rxribbon.sync.JustLocationRepository;

import java.lang.reflect.InvocationTargetException;

public class Main {
  private static final DynamicPropertyFactory propertyFactory = DynamicPropertyFactory.getInstance();

  public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

    Class userRepo = Class.forName(propertyFactory.getStringProperty("userrepo.dev", "").get());
    Class locationRepo = Class.forName(propertyFactory.getStringProperty("locationrepo.dev", "").get());
    Class itemRepo = Class.forName(propertyFactory.getStringProperty("itemrepo.dev", "").get());

    Service service = new Service(
      (UserRepository) userRepo.newInstance(),
      (LocationRepository) locationRepo.newInstance(),
      (ItemRepository) itemRepo.newInstance()
    );

    Server.start(new Controller(service));
  }
}
