package productpage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;

@Singleton
public class ProductsService {

  public Flux<Product> getProducts() {
    return Flux.just(Product.builder()
      .id(0)
      .title("The Comedy of Errors")
      .descriptionHtml("<a href=\"https://en.wikipedia.org/wiki/The_Comedy_of_Errors\">Wikipedia Summary</a>: The Comedy of Errors is one of <b>William Shakespeare's</b> early plays. It is his shortest and one of his most farcical comedies, with a major part of the humour coming from slapstick and mistaken identity, in addition to puns and word play.")
      .build()
    );
  }

  public Mono<Product> getProduct(int productId) {
    return getProducts().collectList()
      .map(products -> {
        if (productId > products.size() - 1) {
          return null;
        }
        return products.get(productId);
      });
  }

}
