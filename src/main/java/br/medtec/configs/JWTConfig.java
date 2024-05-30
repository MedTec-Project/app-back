package br.medtec.configs;

import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

@ApplicationScoped
public class JWTConfig {
    @Produces
    public static JWTAuthContextInfo authContextInfo() {

        return new JWTAuthContextInfo("src/main/resources/publicKey.pem", "https://medtec.com.br/issuer");

    }
}

