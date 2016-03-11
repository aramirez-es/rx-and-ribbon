package es.aramirez.rxribbon;

import es.aramirez.rxribbon.sync.JustItemRepository;
import es.aramirez.rxribbon.sync.JustLocationRepository;
import es.aramirez.rxribbon.sync.JustUserRepository;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import netflix.karyon.Karyon;
import netflix.karyon.ShutdownModule;
import netflix.karyon.transport.http.SimpleUriRouter;

public class Server {
  public static void start() {
    Karyon.forRequestHandler(
      8000,
      new SimpleUriRouter<ByteBuf, ByteBuf>().addUri("/hello",
        (HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) ->

          request.getContent()
            .flatMap(byteBuf ->
              new Service(
                new JustUserRepository(),
                new JustLocationRepository(),
                new JustItemRepository()
              )
                .execute(new ServiceRequest("Person", 42.2, 15.5, 42))
            ).flatMap(aggregated -> {
              response.writeStringAndFlush(aggregated.toString());
              response.writeStringAndFlush(System.lineSeparator());
              return response.close();
            })

        ),
      ShutdownModule.asBootstrapModule()
    ).startAndWaitTillShutdown();
  }
}
