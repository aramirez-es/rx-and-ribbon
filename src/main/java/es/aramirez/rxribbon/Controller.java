package es.aramirez.rxribbon;

import es.aramirez.rxribbon.sync.JustItemRepository;
import es.aramirez.rxribbon.sync.JustLocationRepository;
import es.aramirez.rxribbon.sync.JustUserRepository;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import java.nio.charset.Charset;

public class Controller implements RequestHandler<ByteBuf, ByteBuf> {
  private final Service service;

  public Controller() {
    this.service = new Service(
      new JustUserRepository(),
      new JustLocationRepository(),
      new JustItemRepository()
    );
  }

  @Override
  public Observable<Void> handle(
    HttpServerRequest<ByteBuf> request,
    HttpServerResponse<ByteBuf> response
  ) {
    return request.getContent()
      .flatMap(byteBuf -> Observable.just(byteBuf.toString(Charset.defaultCharset())))
      .flatMap(requestAsString -> service.execute(new ServiceRequest("Person", 42.2, 15.5, 42)))
      .flatMap(aggregated -> setResponse(response, aggregated))
      .finallyDo(response::close);
  }

  private Observable<Void> setResponse(
    HttpServerResponse<ByteBuf> response,
    Aggregated aggregated
  ) {
    response.writeStringAndFlush(aggregated.toString());
    return response.writeStringAndFlush(System.lineSeparator());
  }
}
