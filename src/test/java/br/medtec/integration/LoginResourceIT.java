package br.medtec.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


@QuarkusTest
public class LoginResourceIT {


    @Nested
    @DisplayName("Cadastro")
    public class CadastroTest {

        String payload = """
                {
                   "email": "usuario1@teste.com",
                   "senha": "senha123",
                   "nome": "Usuario Teste",
                   "telefone": "47988035851"
                }
                """;
        @Test
        @DisplayName("Cria usuario com sucesso")
        public void criaUsuarioComSucesso() {
            RestAssured
                    .given()
                    .body(payload)
                    .contentType("application/json")
                    .when()
                    .post("api/cadastrar")
                    .then()
                    .statusCode(201);
        }
    }

    @Nested
    @DisplayName("Login")
    public class LoginTest {

        String jsonCadastro = """
                {
                   "email": "usuario@teste.com",
                   "senha": "senha123",
                   "nome": "Usuario Teste",
                   "telefone": "47988035851"
                }
                """;

        String jsonLogin = """
                    {
                         "email": "usuario@teste.com",
                         "senha": "senha123"
                    }
                """;
        @Test
        @DisplayName("Login com sucesso")
        public void loginComSucesso() {
            RestAssured
                    .given()
                    .body(jsonCadastro)
                    .contentType("application/json")
                    .when()
                    .post("api/cadastrar")
                    .then()
                    .statusCode(201);


            RestAssured
                    .given()
                    .body(jsonLogin)
                    .contentType("application/json")
                    .when()
                    .post("api/login")
                    .then()
                    .statusCode(200);
        }
    }
}
