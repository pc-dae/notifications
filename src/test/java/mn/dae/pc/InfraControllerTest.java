package mn.dae.pc;

import mn.dae.pc.model.Infra;
import mn.dae.pc.repository.InfraRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
@Testcontainers
// JPA drop and create table, good for testing
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=create-drop"})
public class InfraControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    InfraRepository infraRepository;

    // static, all tests share this postgres container
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        infraRepository.deleteAll();

        Infra i1 = new Infra("vm", "paul1");
        Infra i2 = new Infra("vm", "paul2");

        infraRepository.saveAll(List.of(i1, i2));
    }

    @Test
    void testFindAll() {

        given()
                .contentType(ContentType.JSON)
                .when()
                    .get("/infra")
                .then()
                    .statusCode(200)    // expecting HTTP 200 OK
                    .contentType(ContentType.JSON) // expecting JSON response content
                    .body(".", hasSize(2));

    }

    @Test
    void testFindByTypeAndName() {

        String name = "paul1";
        String type = "vm";

        given()
                //Returning floats and doubles as BigDecimal
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(ContentType.JSON)
                .pathParam("type", type)
                .pathParam("name", name)
                .when()
                    .get("/infra/find/{type}/{name}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                        ".", hasSize(1),
                        "[0].type", equalTo("vm"),
                        "[0].name", equalTo("paul1")
                );
    }

    @Test
    public void testDeleteById() {
        Long id = 1L; // replace with a valid ID
        given()
                .pathParam("id", id)
                .when()
                    .delete("/infra/{id}")
                .then()
                    .statusCode(204); // expecting HTTP 204 No Content
    }

    @Test
    public void testCreate() {

        given()
                .contentType(ContentType.JSON)
                .body("{ \"type\": \"vm\", \"name\": \"paul3\" }")
                .when()
                    .post("/infra")
                .then()
                    .statusCode(201) // expecting HTTP 201 Created
                    .contentType(ContentType.JSON); // expecting JSON response content

        // find the new saved infra
        given()
                //Returning floats and doubles as BigDecimal
                .config(RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL)))
                .contentType(ContentType.JSON)
                .pathParam("type", "vm")
                .pathParam("name", "paul3")
                .when()
                    .get("/infra/find/{type}/{name}")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(
                        ".", hasSize(1),
                        "[0].type", equalTo("vm"),
                        "[0].name", equalTo("paul3")
                    );
    }

    @Test
    public void testUpdate() {

        Infra infraVmPaul1 = infraRepository.findByTypeAndName("vm", "paul1").get(0);
        System.out.println(infraVmPaul1);

        Long id = infraVmPaul1.getId();

        infraVmPaul1.setType("k8s");
        infraVmPaul1.setName("paul4");

        given()
                .contentType(ContentType.JSON)
                .body(infraVmPaul1)
                .when()
                    .put("/infra")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON);

        // get the updated infra
        Infra updatedInfra = infraRepository.findById(id).orElseThrow();
        System.out.println(updatedInfra);

        assertEquals(id, updatedInfra.getId());
        assertEquals("k8s", updatedInfra.getType());
        assertEquals("paul4", updatedInfra.getName());
    }


}