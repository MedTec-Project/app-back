package br.medtec.integration;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class LoginResourceIT {

    @Nested
    @DisplayName("Cadastro")
    public class CadastroTest {


        private String JSON_BODY = """
                {
                    "email": "teste@gmail.com",
                    "senha": "123456",
                    "telefone": "12345678911",
                    "nome": "Teste"
                }
                """;

        @Test
        @DisplayName("Cria usuario com sucesso")
        public void criaUsuarioComSucesso() {
            given()
                    .body(JSON_BODY)
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(200);
        }

        @Test
        @DisplayName("Email já cadastrado")
        public void emailJaCadastrado() {
            given()
                    .body(JSON_BODY)
                    .when()
                    .post("/login")
                    .then()
                    .statusCode(400);
        }
    }
}
