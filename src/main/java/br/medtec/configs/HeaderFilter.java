package br.medtec.configs;

import br.medtec.utils.JWTUtils;
import br.medtec.utils.StringUtil;
import br.medtec.utils.UserSession;
import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Provider
public class HeaderFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();

        String token = headers.getFirst("Authorization");
        if (StringUtil.isValidString(token)) {
            token = token.replace("Bearer ", "");

            JWTAuthContextInfo jwtAuthContextInfo = JWTConfig.authContextInfo();
            JWTUtils jwtUtils = new JWTUtils(jwtAuthContextInfo);

            String oidUsuario = jwtUtils.getClaim("oidUser");
            String tipoUsuario = jwtUtils.getGroups() == null ? null : jwtUtils.getGroups().iterator().next();

            UserSession.getInstance().setOidUser(oidUsuario);
            UserSession.getInstance().setToken(token);
            UserSession.getInstance().setUserType(tipoUsuario);
//
//            if (!isUserAllowed(tipoUsuario, requestContext)) {
//                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
//                        .entity("Acesso negado!").build());
//            }
        }
    }

    private boolean isUserAllowed(String userRole, ContainerRequestContext requestContext) {
        Class<?> resourceClass = requestContext.getUriInfo().getMatchedResources().get(0).getClass();

        RolesAllowed rolesAllowed = resourceClass.getAnnotation(RolesAllowed.class);

        if (rolesAllowed != null) {
            Set<String> allowedRoles = new HashSet<>(List.of(rolesAllowed.value()));
            return allowedRoles.contains(userRole);
        }
        return true;
    }
}
