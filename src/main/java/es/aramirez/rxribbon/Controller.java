package es.aramirez.rxribbon;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.RequestHandler;
import rx.Observable;

import org.apache.commons.io.FileUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller implements RequestHandler<ByteBuf, ByteBuf> {
  private final Service service;
  private AtomicBoolean isInitialized = new AtomicBoolean(false);

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
      .doOnEach(notification -> printMemoryInformation())
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

  private synchronized void printMemoryInformation() {
    if (isInitialized.getAndSet(true)) {
      System.out.print(String.format("%c[%dA", 0x1B, 5));
      System.out.print(String.format("%c[%dD", 0x1B, 50));
    }
    System.out.println("#======================#");
    System.out.println(String.format("# Free Memory:  %s #", humanize(Runtime.getRuntime().freeMemory())));
    System.out.println(String.format("# Total Memory: %s #", humanize(Runtime.getRuntime().totalMemory())));
    System.out.println(String.format("# Max Memory:   %s   #", humanize(Runtime.getRuntime().maxMemory())));
    System.out.println("#======================#");
  }

  private String humanize(long bytes) {
    return FileUtils.byteCountToDisplaySize(bytes);
  }
}
