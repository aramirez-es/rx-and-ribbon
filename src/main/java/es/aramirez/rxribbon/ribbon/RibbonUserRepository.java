package es.aramirez.rxribbon.ribbon;

import com.netflix.ribbon.http.HttpRequestTemplate;
import es.aramirez.rxribbon.User;
import es.aramirez.rxribbon.UserRepository;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.ws.rs.HttpMethod;

public class RibbonUserRepository extends RibbonCommand implements UserRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(RibbonUserRepository.class);

  private static final String RESOURCE_GROUP = "userService";
  private static final String TEMPLATE = "getPokemon";
  private final HttpRequestTemplate<ByteBuf> template;

  public RibbonUserRepository() {
    super(RESOURCE_GROUP);
    template = templateFor(TEMPLATE, HttpMethod.GET, "/api/v2/pokemon/{id}/");
  }

  @Override
  public Observable<User> findByName(String userName) {
    return template.requestBuilder()
      .withRequestProperty("id", randomInt(811))
      .build()
      .toObservable()
      .map(this::byteBufToString)
      .map(contentAsString -> new User(userName))
      .doOnError(throwable -> LOGGER.error(throwable.getMessage()));
  }
}
