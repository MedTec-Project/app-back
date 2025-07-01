package br.medtec.features.user;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.image.ImageService;
import br.medtec.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.InputStream;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends GenericsResource {

    private final UserService userService;

    private final ImageService imageService;

    public UserResource() {
        this.userService = null; // CDI will inject this
        this.imageService = null; // CDI will inject this
    }

    @Inject
    public UserResource(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @POST
    @Path("login")
    @PermitAll
    @Operation(summary = "Login")
    public Response login(UserLoginDTO userDTO) {
        try {
            return ResponseUtils.ok(userService.login(userDTO));
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
            if (userService.checkIfExists(userDTO)) {
                return ResponseUtils.badRequest("Esse email já está cadastrado");
            } else {
                return ResponseUtils.created(userService.createUser(userDTO));
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("/user")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Get User")
    public Response getUser() {
        try {
            String oidUser = UserSession.getOidUser();
            UserDTO user = userService.getUser(oidUser);
            if (user == null) {
                return ResponseUtils.notFound("Usuário não encontrado");
            }
            return ResponseUtils.ok(user);
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @POST
    @Path("/user/photo")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Upload User Photo")
    public Response uploadUserPhoto(UserDTO userDTO) {
        try {
            userService.uploadUserPhoto(userDTO);
            return ResponseUtils.ok("Foto atualizada com sucesso!");
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("/user/{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Update User")
    public Response update(UserDTO userDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(userService.updateUser(userDTO, oid));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
