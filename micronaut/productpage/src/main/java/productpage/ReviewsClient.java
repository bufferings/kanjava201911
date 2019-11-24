package productpage;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client("${reviews.url}")
public interface ReviewsClient {

  @Get("{productId}")
  public Mono<Reviews> getReviews(int productId);

}
