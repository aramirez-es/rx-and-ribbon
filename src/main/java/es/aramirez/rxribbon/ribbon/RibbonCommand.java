package es.aramirez.rxribbon.ribbon;

import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.ribbon.ClientOptions;
import com.netflix.ribbon.Ribbon;
import com.netflix.ribbon.http.HttpRequestTemplate;
import com.netflix.ribbon.http.HttpResourceGroup;
import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.Random;

public abstract class RibbonCommand {
  private final HttpResourceGroup commandGroup;

  public RibbonCommand(String resourceGroup) {
    commandGroup = Ribbon.createHttpResourceGroup(
      resourceGroup,
      ClientOptions.from(new DefaultClientConfigImpl())
    );
  }

  protected HttpRequestTemplate<ByteBuf> templateFor(String template, String method, String url) {
    return commandGroup.newTemplateBuilder(template, ByteBuf.class)
      .withMethod(method)
      .withUriTemplate(url)
      .withHystrixProperties(
        HystrixObservableCommand.Setter
          .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandGroup.name()))
          .andCommandKey(HystrixCommandKey.Factory.asKey(template))
          .andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter()
          )
      )
      .build();
  }

  protected String byteBufToString(ByteBuf toTransform) {
    return toTransform.toString(Charset.defaultCharset());
  }

  protected int randomInt(int upTo) {
    return new Random().nextInt(upTo);
  }
}
