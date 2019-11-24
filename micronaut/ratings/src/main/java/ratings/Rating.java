package ratings;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ratings")
@Getter
@Setter
public class Rating {
  @Id
  private Integer reviewid;
  private Integer rating;
}
