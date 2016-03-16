package es.aramirez.rxribbon;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class Controller implements RequestHandler<ByteBuf, ByteBuf> {
  private final Service service;

  public Controller(Service service) {
    this.service = service;
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
      .switchIfEmpty(emptyResponse(response))
      .onErrorResumeNext(throwable -> setErrorResponse(response, throwable))
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

  private Observable<Void> setErrorResponse(
    HttpServerResponse<ByteBuf> response,
    Throwable throwable
  ) {
    response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
    response.writeStringAndFlush(throwable.getMessage());
    return response.writeStringAndFlush(System.lineSeparator());
  }

  private Observable<Void> emptyResponse(HttpServerResponse<ByteBuf> response) {
    return Observable.defer(() -> {
      response.setStatus(HttpResponseStatus.NO_CONTENT);
      return Observable.empty();
    });
  }
}
