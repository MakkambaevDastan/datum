package datum.app.admin.controller;

import datum.CommonPostgresqlContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
@Sql("/init-test.sql")
//@Transactional
//@Testcontainers
class EndpointControllerIT {
    @Autowired
    private WebTestClient webClient;
//    @Autowired
//    private MockMvc mockMvc;
//    @LocalServerPort
//    private int port;
    private static final String BASE_URL = "/ADMIN/endpoint";
    @Container
    static PostgreSQLContainer<?> postgres = CommonPostgresqlContainer.getInstance();

    @Test
    void contextLoads() throws Exception {
//        mockMvc.perform(get(BASE_URL)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }
}