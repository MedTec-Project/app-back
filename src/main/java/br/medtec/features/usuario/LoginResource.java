package br.medtec.features.usuario;


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
    public Response login(UsuarioLoginDTO usuarioDTO) {
        try {
            return ResponseUtils.ok(loginService.login(usuarioDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @POST
    @Path("cadastrar")
    @PermitAll
    @Schema(hidden = true)
    @Operation(summary = "Cadastrar Usuário")
    public Response cadastrar(UsuarioDTO usuarioDTO) {
        try {
            if (loginService.verificaExiste(usuarioDTO)) {
                return ResponseUtils.badRequest("Esse email já está cadastrado");
            } else {
                Usuario usuario = loginService.criaUsuario(usuarioDTO);
                return ResponseUtils.created(usuario);
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
