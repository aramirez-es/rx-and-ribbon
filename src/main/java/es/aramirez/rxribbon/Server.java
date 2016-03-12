package es.aramirez.rxribbon;

import io.netty.buffer.ByteBuf;
import netflix.karyon.Karyon;
import netflix.karyon.ShutdownModule;
import netflix.karyon.transport.http.SimpleUriRouter;

public class Server {
  public static void start(Controller controller) {

    SimpleUriRouter<ByteBuf, ByteBuf> router =
      new SimpleUriRouter<ByteBuf, ByteBuf>()
        .addUri("/hello", controller);

    Karyon
      .forRequestHandler(8000, router, ShutdownModule.asBootstrapModule())
      .startAndWaitTillShutdown();
  }
}
