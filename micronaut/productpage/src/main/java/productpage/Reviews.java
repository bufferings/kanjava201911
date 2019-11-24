package productpage;

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
public class Reviews {
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

    private List<String> redStars;
    private List<String> whiteStars;

    public void generateStars() {
      redStars = new ArrayList<>();
      for (int i = 0; i < stars; i++) {
        redStars.add("");
      }
      whiteStars = new ArrayList<>();
      for (int i = 5; i > stars; i--) {
        whiteStars.add("");
      }
    }
  }
}
