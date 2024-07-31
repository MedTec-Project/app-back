package br.medtec.usuario;


import br.medtec.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    @Schema(example = """
            {
                "email": fernandesrichard312@gmail.com",
                "senha": "123456"
            }
            """)
    public Response login(UsuarioDTO usuarioDTO) {
        if (!loginService.verificaExiste(usuarioDTO, true)){
            return ResponseUtils.badRequest("Email ou Senha Incorreto");
        } else {
            return ResponseUtils.ok(loginService.login(usuarioDTO));
        }

    }

    @POST
    @Path("cadastrar")
    @PermitAll
    @Schema(hidden = true)
    public Response cadastrar(UsuarioDTO usuarioDTO) {
        if (loginService.verificaExiste(usuarioDTO, false)) {
            return ResponseUtils.badRequest("Esse email já está cadastrado");
        } else {
            Usuario usuario = loginService.criaUsuario(usuarioDTO);
            return ResponseUtils.created(usuario);
        }
    }
}
