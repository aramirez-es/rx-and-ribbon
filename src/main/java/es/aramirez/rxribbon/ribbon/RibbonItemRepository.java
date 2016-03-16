package es.aramirez.rxribbon.ribbon;

import com.netflix.ribbon.http.HttpRequestTemplate;
import es.aramirez.rxribbon.Item;
import es.aramirez.rxribbon.ItemRepository;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.ws.rs.HttpMethod;

public class RibbonItemRepository extends RibbonCommand implements ItemRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(RibbonItemRepository.class);

  private static final String RESOURCE_GROUP = "itemService";
  private static final String TEMPLATE = "getItem";
  private final HttpRequestTemplate<ByteBuf> template;

  public RibbonItemRepository() {
    super(RESOURCE_GROUP);
    template = templateFor(TEMPLATE, HttpMethod.GET, "/api/v2/item/{id}/");
  }

  @Override
  public Observable<Item> findById(Integer id) {
    return template.requestBuilder()
      .withRequestProperty("id", randomInt(746))
      .build()
      .toObservable()
      .map(this::byteBufToString)
      .map(contentAsString -> new Item(id))
      .doOnError(throwable -> LOGGER.error(throwable.getMessage()));
  }
}
