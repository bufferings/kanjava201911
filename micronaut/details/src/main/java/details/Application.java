package details;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Controller
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }

  @Get("/health")
  public HttpResponse getHealth() {
    return HttpResponse.ok();
  }

  @Get("/details/{productId}")
  public Mono<Details> getDetails(int productId) {
    return Mono.just(Details.create(productId));
  }

  @Introspected
  @Data
  @NoArgsConstructor
  public static class Details {

    static Details create(int productId) {
      Details details = new Details();
      details.id = productId;
      return details;
    }

    private Integer id;
    private String author = "William Shakespeare";
    private Integer year = 1595;
    private String type = "paperback";
    private Integer pages = 200;
    private String publisher = "PublisherA";
    private String language = "English";
    @JsonProperty("ISBN-10")
    private String isbn10 = "1234567890";
    @JsonProperty("ISBN-13")
    private String isbn13 = "123-1234567890";
  }

}