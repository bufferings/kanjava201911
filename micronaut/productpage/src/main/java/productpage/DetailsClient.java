package productpage;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client("${details.url}")
public interface DetailsClient {

  @Get("{productId}")
  public Mono<Details> getDetails(int productId);

}
