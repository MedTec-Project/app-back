package br.medtec.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class ResourceITConfig {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:9000";
        RestAssured.basePath = "/";
    }
}
