package reviews;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client("${ratings.url}")
public interface RatingsClient {

  @Get("{productId}")
  Mono<HttpResponse<String>> getRatings(int productId);
}
