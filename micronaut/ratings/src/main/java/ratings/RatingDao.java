package ratings;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface RatingDao extends CrudRepository<Rating, Integer> {
}
