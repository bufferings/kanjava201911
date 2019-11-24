package ratings;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.dialect.PostgreSQL10Dialect;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@TypeHint({
  org.postgresql.Driver.class,
  PostgreSQL10Dialect.class
})
@Controller
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }

  private final RatingDao ratingDao;

  public Application(RatingDao ratingDao) {
    this.ratingDao = ratingDao;
  }

  @Get("/health")
  public HttpResponse getHealth() {
    return HttpResponse.ok();
  }

  @Get("/ratings/{productId}")
  public Response getRatings(int productId) {
    Iterator<Rating> ratings = ratingDao.findAll().iterator();
    return Response.create(
      productId, ratings.next().getRating(), ratings.next().getRating());
  }

  @Introspected
  @Getter
  @Setter
  @NoArgsConstructor
  public static class Response {

    static Response create(int productId, int reviewer1, int reviewer2) {
      Response response = new Response();
      response.productId = productId;
      response.ratings = new LinkedHashMap<>();
      response.ratings.put("Reviewer1", reviewer1);
      response.ratings.put("Reviewer2", reviewer2);
      return response;
    }

    private int productId;

    private Map<String, Integer> ratings;
  }

}