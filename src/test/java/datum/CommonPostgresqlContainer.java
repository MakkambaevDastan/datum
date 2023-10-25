package datum;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

//@ExtendWith({SpringExtension.class})
//@SpringBootTest
//@Testcontainers
public class CommonPostgresqlContainer extends PostgreSQLContainer<CommonPostgresqlContainer> {
    private static final String VERSION = "postgres:15-alpine";
    private static CommonPostgresqlContainer container;
    private CommonPostgresqlContainer() {
        super(VERSION);
    }
    public static CommonPostgresqlContainer getInstance() {
        if (container == null) {
            container = new CommonPostgresqlContainer();
        }
        return container;
    }
    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }
    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
