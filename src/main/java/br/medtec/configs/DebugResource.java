package br.medtec.configs;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/debug")
public class DebugResource {

    @Context
    SecurityContext securityContext;

    @GET
    public String getUserRoles() {
        Principal userPrincipal = securityContext.getUserPrincipal();
        if (userPrincipal != null) {
            return "Usuário autenticado: " + userPrincipal.getName() +
                    "\nRole Admin? " + securityContext.isUserInRole("admin") +
                    "\nRole User? " + securityContext.isUserInRole("user");
        }
        return "Usuário não autenticado.";
    }
}
