package productpage;

import io.micronaut.core.annotation.TypeHint;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;
import io.micronaut.views.View;
import org.thymeleaf.standard.expression.AdditionExpression;
import org.thymeleaf.standard.expression.AdditionSubtractionExpression;
import org.thymeleaf.standard.expression.AndExpression;
import org.thymeleaf.standard.expression.DivisionExpression;
import org.thymeleaf.standard.expression.EqualsExpression;
import org.thymeleaf.standard.expression.EqualsNotEqualsExpression;
import org.thymeleaf.standard.expression.GreaterLesserExpression;
import org.thymeleaf.standard.expression.GreaterOrEqualToExpression;
import org.thymeleaf.standard.expression.GreaterThanExpression;
import org.thymeleaf.standard.expression.LessOrEqualToExpression;
import org.thymeleaf.standard.expression.LessThanExpression;
import org.thymeleaf.standard.expression.MultiplicationDivisionRemainderExpression;
import org.thymeleaf.standard.expression.MultiplicationExpression;
import org.thymeleaf.standard.expression.NotEqualsExpression;
import org.thymeleaf.standard.expression.OrExpression;
import org.thymeleaf.standard.expression.RemainderExpression;
import org.thymeleaf.standard.expression.SubtractionExpression;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@TypeHint(value = {
  // Thymeleaf
  MultiplicationDivisionRemainderExpression.class,
  MultiplicationExpression.class,
  RemainderExpression.class,
  DivisionExpression.class,
  AndExpression.class,
  AdditionSubtractionExpression.class,
  AdditionExpression.class,
  SubtractionExpression.class,
  GreaterLesserExpression.class,
  LessOrEqualToExpression.class,
  GreaterOrEqualToExpression.class,
  LessThanExpression.class,
  GreaterThanExpression.class,
  EqualsNotEqualsExpression.class,
  EqualsExpression.class,
  NotEqualsExpression.class,
  OrExpression.class
})
@Controller
public class Application {

  public static void main(String[] args) {
    Micronaut.run(Application.class);
  }

  private final ProductsService productsService;

  private final DetailsClient detailsClient;

  private final ReviewsClient reviewsClient;

  public Application(ProductsService productsService,
                     DetailsClient detailsClient,
                     ReviewsClient reviewsClient) {
    this.productsService = productsService;
    this.detailsClient = detailsClient;
    this.reviewsClient = reviewsClient;
  }

  @Get("/health")
  public HttpResponse getHealth() {
    return HttpResponse.ok();
  }

  @View("productpage")
  @Get("/productpage")
  Mono<HttpResponse> getProductPage() {
    int productId = 0;
    return Mono.zip(
      productsService.getProduct(productId),
      detailsClient.getDetails(productId),
      reviewsClient.getReviews(productId)
    ).map(t -> {
      // trick for Thymeleaf
      t.getT3().getReviews().forEach(it -> it.getRating().generateStars());
      return HttpResponse.ok(CollectionUtils.mapOf(
        "product", t.getT1(),
        "details", t.getT2(),
        "reviews", t.getT3()));
    });
  }

  @Get("/api/v1/products")
  public Flux<Product> getProducts() {
    return productsService.getProducts();
  }

  @Get("/api/v1/products/{productId}")
  public Mono<Details> getProduct(int productId) {
    return detailsClient.getDetails(productId);
  }

  @Get("/api/v1/products/{productId}/reviews")
  public Mono<Reviews> getReviews(int productId) {
    return reviewsClient.getReviews(productId);
  }
}