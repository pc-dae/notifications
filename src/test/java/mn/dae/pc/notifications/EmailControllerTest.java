package mn.dae.pc.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import mn.dae.pc.notifications.entity.Email;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// activate automatic startup and stop of containers
@Testcontainers
public class EmailControllerTest {

    public String hmtlText() {
        return """
        <!DOCTYPE html>
<html lang="en">
<head>
 <meta charset="UTF-8">
 <title>Greeting</title>
</head>
<body>
 <h1>Hello, {{name}}!</h1>
</body>
</html>""";
    }

    private String objToJson(Object o) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(o);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    public void testSend() {
        Map<String, String> data1 = Map.ofEntries(
                entry("title", "This is a message title"),
                entry("body", "This is the message body")
        );
        Email e1 = new Email("paul.carlton@dae.mn", data1, "", hmtlText());
        Email e2 = new Email("paul.carlton@dae.mn", data1, "t1", "");


        given()
                .contentType(ContentType.JSON)
                .body(objToJson(e1))
                .when()
                .post("/email")
                .then()
                .statusCode(201); // expecting HTTP 201 Created
    }

}