package br.medtec.utils;

import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationPath("/api")
public class GenericsResource {
    @Inject
    SecurityContext securityContext;

    public String criaSessao() {
        JsonWebToken jwt = (JsonWebToken) securityContext.getUserPrincipal();
       Sessao.getInstance().setOidUsuario(jwt.getClaim("oidUsuario"));
        return jwt.getClaim("oidUsuario");
    }



}
