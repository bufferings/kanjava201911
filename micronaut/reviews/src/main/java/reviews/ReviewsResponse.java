package reviews;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Introspected
@Getter
@Setter
@NoArgsConstructor
public class ReviewsResponse {

  private String id;
  private List<Review> reviews;

  @Introspected
  @Getter
  @Setter
  @NoArgsConstructor
  public static class Review {
    private String reviewer;
    private String text;
    private Rating rating;
  }

  @Introspected
  @Getter
  @Setter
  @NoArgsConstructor
  public static class Rating {
    private Integer stars;
    private String color;
    private String error;

    public Rating(int value) {
      if (value != -1) {
        stars = value;
        color = "red";
      } else {
        error = "Ratings service is currently unavailable";
      }
    }
  }

  public static ReviewsResponse getJsonResponse(int productId, int starsReviewer1, int starsReviewer2) {
    ReviewsResponse response = new ReviewsResponse();
    response.id = Integer.toString(productId);
    response.reviews = new ArrayList<>();

    Review review1 = new Review();
    review1.reviewer = "Reviewer1";
    review1.text = "An extremely entertaining play by Shakespeare. The slapstick humour is refreshing!";
    review1.rating = new Rating(starsReviewer1);
    response.reviews.add(review1);

    Review review2 = new Review();
    review2.reviewer = "Reviewer2";
    review2.text = "Absolutely fun and entertaining. The play lacks thematic depth when compared to other plays by Shakespeare.";
    review2.rating = new Rating(starsReviewer2);
    response.reviews.add(review2);

    return response;
  }
}
