package es.aramirez.rxribbon;

import org.apache.commons.io.FileUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class InformationPrinter {
  private static AtomicBoolean isInitialized = new AtomicBoolean(false);

  public static void printInformation() {
    Observable
      .timer(5, TimeUnit.SECONDS)
      .concatWith(Observable.interval(1, TimeUnit.SECONDS))
      .doOnEach(notification -> printMemoryInformation())
      .observeOn(Schedulers.computation())
      .subscribe();
  }

  private static synchronized void printMemoryInformation() {
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

  private static String humanize(long bytes) {
    return FileUtils.byteCountToDisplaySize(bytes);
  }
}
