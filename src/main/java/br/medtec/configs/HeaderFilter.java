package br.medtec.configs;

import br.medtec.utils.JWTUtils;
import br.medtec.utils.Sessao;
import br.medtec.utils.UtilString;
import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;

@Provider
public class HeaderFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();

        String token = headers.getFirst("Authorization");
        if (UtilString.stringValida(token)) {

            token = token.replace("Bearer ", "");

            JWTAuthContextInfo jwtAuthContextInfo = JWTConfig.authContextInfo();
            JWTUtils jwtUtils = new JWTUtils(jwtAuthContextInfo);

            Sessao.getInstance().setOidUsuario(jwtUtils.getClaim("oidUsuario"));
            Sessao.getInstance().setToken(token);

        }

    }
}
