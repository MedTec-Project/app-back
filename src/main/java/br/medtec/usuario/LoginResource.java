package br.medtec.usuario;


import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class LoginResource extends GenericsResource {

    @Inject
    LoginService loginService;

    @POST
    @Path("login")
    @PermitAll
    @Operation(summary = "Login")
    public Response login(UsuarioLoginDTO usuarioDTO) {
        try {
            String token = loginService.login(usuarioDTO);
            return ResponseUtils.ok(token);
        } catch (MEDBadRequestExecption e) {
            log.error("Erro ao logar {}", e.getMessage());
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
            Usuario usuario = loginService.criaUsuario(usuarioDTO);
            return ResponseUtils.created(usuario);
        } catch (Exception e) {
            log.error("Erro ao cadastrar {}", e.getMessage());
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
