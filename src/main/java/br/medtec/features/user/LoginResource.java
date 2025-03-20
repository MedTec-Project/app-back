package br.medtec.features.user;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource extends GenericsResource {

    @Inject
    LoginService loginService;

    @POST
    @Path("login")
    @PermitAll
    @Operation(summary = "Login")
    public Response login(UserLoginDTO userDTO) {
        try {
            return ResponseUtils.ok(loginService.login(userDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @POST
    @Path("register")
    @PermitAll
    @Schema(hidden = true)
    @Operation(summary = "Register User")
    public Response register(UserDTO userDTO) {
        try {
            if (loginService.checkIfExists(userDTO)) {
                return ResponseUtils.badRequest("Esse email já está cadastrado");
            } else {
                User user = loginService.createUser(userDTO);
                return ResponseUtils.created(user);
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
