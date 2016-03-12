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
import java.util.List;
import java.util.Map;

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
      .map(byteBuf -> toServiceRequest(request))
      .flatMap(service::execute)
      .flatMap(aggregated -> setResponse(response, aggregated))
      .finallyDo(response::close);
  }

  private ServiceRequest toServiceRequest(HttpServerRequest<ByteBuf> request) {
    Map<String, List<String>> queryString = request.getQueryParameters();
    return new ServiceRequest(
      queryString.get("user").get(0),
      Double.valueOf(queryString.get("latitude").get(0)),
      Double.valueOf(queryString.get("longitude").get(0)),
      Integer.valueOf(queryString.get("itemId").get(0)));
  }

  private Observable<Void> setResponse(
    HttpServerResponse<ByteBuf> response,
    Aggregated aggregated
  ) {
    response.writeStringAndFlush(aggregated.toString());
    return response.writeStringAndFlush(System.lineSeparator());
  }
}
