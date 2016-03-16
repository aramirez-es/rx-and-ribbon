package es.aramirez.rxribbon;

import com.netflix.config.DynamicPropertyFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
  private static final String ENV_KEY = "env";
  private static final String USER_REPO = "userrepo";
  private static final String LOCATION_REPO = "locationrepo";
  private static final String ITEM_REPO = "itemrepo";

  private static final DynamicPropertyFactory propertyFactory = DynamicPropertyFactory.getInstance();

  private static final List<String> validEnvironments = new ArrayList<String>() {{
    add("sync");
    add("async");
    add("latency");
    add("ribbon");
    add("take");
  }};

  public static void main(String[] args) {

    try {
      String env = getEnvOrThrowError();

      Class userRepo = Class.forName(propertyFactory.getStringProperty(getRepo(USER_REPO, env), "").get());
      Class locationRepo = Class.forName(propertyFactory.getStringProperty(getRepo(LOCATION_REPO, env), "").get());
      Class itemRepo = Class.forName(propertyFactory.getStringProperty(getRepo(ITEM_REPO, env), "").get());

      Service service = new Service(
        (UserRepository) userRepo.newInstance(),
        (LocationRepository) locationRepo.newInstance(),
        (ItemRepository) itemRepo.newInstance()
      );

      InformationPrinter.printInformation();
      Server.start(new Controller(service));
    } catch (Exception e) {
      System.err.println(e);
      System.exit(-1);
    }
  }

  private static String getRepo(String whatRepo, String env) {
    return String.format("%s.%s", whatRepo, env);
  }

  private static String getEnvOrThrowError() {
    String env = System.getProperty(ENV_KEY);

    if (validEnvironments.contains(env)) {
      return env;
    }

    throw new IllegalArgumentException(String.format("\"%s\" is not a valid environment", env));
  }
}
