package aulas.web.adivinhe;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AdivinheTest {

    @Test
    public void testStatus() {
        given()
          .when().get("/status")
          .then()
             .statusCode(200)
             .body(is("Serviço ativo"));
    }

    @Test
    public void testInfoJogador() {
        given()
            .auth().basic("jsilva", "s3gr3d2")
            .when().get("/jogador/info/2")
            .then()
                .statusCode(200)
                .body(containsString("jsilva"));
    }
    
    @Test
    public void testFindJogos() {
        given()
            .auth().basic("jsilva", "s3gr3d2")
            .when().get("/jogo/jogador/2")
            .then()
                .statusCode(200)
                .body(containsString("\"jogador\":2"));
    }
}