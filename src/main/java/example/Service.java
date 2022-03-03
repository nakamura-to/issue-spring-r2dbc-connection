package example;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.r2dbc.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Objects;

@RestController
@Transactional
public class Service {

  private final ConnectionFactory connectionFactory;

  public Service(ConnectionFactory connectionFactory) {
    this.connectionFactory = Objects.requireNonNull(connectionFactory);
  }

  @GetMapping("/")
  public Flux<String> ok() {
    return select(connectionFactory);
  }

  @GetMapping("/ng")
  public Flux<String> ng() {
    var proxy = new TransactionAwareConnectionFactoryProxy(connectionFactory);
    return select(proxy);
  }

  private Flux<String> select(ConnectionFactory connectionFactory) {
    return DatabaseClient.create(connectionFactory)
        .sql("SELECT text FROM message")
        .fetch()
        .all()
        .map(m -> m.get("text").toString());
  }
}
