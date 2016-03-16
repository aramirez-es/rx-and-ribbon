package es.aramirez.rxribbon.ribbon;

import com.netflix.ribbon.http.HttpRequestTemplate;
import es.aramirez.rxribbon.Location;
import es.aramirez.rxribbon.LocationRepository;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import javax.ws.rs.HttpMethod;

public class RibbonLocationRepository extends RibbonCommand implements LocationRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(RibbonLocationRepository.class);

  private static final String RESOURCE_GROUP = "locationService";
  private static final String TEMPLATE = "getHabitat";
  private final HttpRequestTemplate<ByteBuf> template;

  public RibbonLocationRepository() {
    super(RESOURCE_GROUP);
    template = templateFor(TEMPLATE, HttpMethod.GET, "/api/v2/pokemon-habitat/{id}/");
  }

  @Override
  public Observable<Location> create(Double latitude, Double longitude) {
    return template.requestBuilder()
      .withRequestProperty("id", randomInt(9))
      .build()
      .toObservable()
      .map(this::byteBufToString)
      .map(contentAsString -> new Location(latitude, longitude))
      .doOnError(throwable -> LOGGER.error(throwable.getMessage()));
  }
}
