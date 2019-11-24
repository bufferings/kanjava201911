package productpage;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Introspected
@Getter
@Setter
@NoArgsConstructor
public class Details {
  private Integer id;
  private String author;
  private Integer year;
  private String type;
  private Integer pages;
  private String publisher;
  private String language;
  @JsonProperty("ISBN-10")
  private String isbn10;
  @JsonProperty("ISBN-13")
  private String isbn13;
}