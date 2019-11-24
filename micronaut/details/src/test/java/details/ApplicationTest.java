package details;

import details.Application.Details;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class ApplicationTest {

  @Inject
  @Client("/")
  RxHttpClient client;

  @Test
  public void testHealth() throws Exception {
    assertEquals(HttpStatus.OK,
      client.toBlocking().exchange("/health").status());
  }

  @Test
  public void testIndex() throws Exception {
    Details details = client.toBlocking()
      .exchange("/details/1", Details.class).body();
    assertEquals(1, details.getId());
  }
}