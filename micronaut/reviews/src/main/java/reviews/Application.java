package reviews;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Controller
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final RatingsClient ratingsClient;

  public Application(RatingsClient ratingsClient) {
    this.ratingsClient = ratingsClient;
  }

  @Get("/health")
  public HttpResponse getHealth() {
    return HttpResponse.ok();
  }

  @Get("/reviews/{productId}")
  public Mono<HttpResponse<ReviewsResponse>> index(int productId) {
    return ratingsClient.getRatings(productId)
      .map(response -> {
        if (response.getStatus() != HttpStatus.OK) {
          return null;
        }
        return response.getBody().map(body -> {
          try {
            return objectMapper.readTree(body);
          } catch (IOException e) {
            return null;
          }
        }).orElse(null);
      })
      .map(ratingsResponse -> {
        int starsReviewer1 = -1;
        int starsReviewer2 = -1;
        if (ratingsResponse != null) {
          if (ratingsResponse.has("ratings")) {
            JsonNode ratings = ratingsResponse.get("ratings");
            if (ratings.has("Reviewer1")) {
              starsReviewer1 = ratings.get("Reviewer1").asInt();
            }
            if (ratings.has("Reviewer2")) {
              starsReviewer2 = ratings.get("Reviewer2").asInt();
            }
          }
        }
        return ReviewsResponse.getJsonResponse(productId, starsReviewer1, starsReviewer2);
      }).map(HttpResponse::ok);
  }

}