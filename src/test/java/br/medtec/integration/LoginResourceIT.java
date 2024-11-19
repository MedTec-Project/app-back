package br.medtec.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class LoginResourceIT {

    @Nested
    @DisplayName("Cadastro")
    public class CadastroTest {
        @Test
        @DisplayName("Cria usuario com sucesso")
        public void criaUsuarioComSucesso() {
            String payload = """
                {
                    "email": "usuario@teste.com",
                    "senha": "senha123"
                }
                """;
            RestAssured
                    .given()
                        .body(payload)
                        .contentType("application/json")
                    .when()
                        .post("api/login")
                    .then()
                        .statusCode(200);
        }
    }
}
