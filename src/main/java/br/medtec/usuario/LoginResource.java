package br.medtec.usuario;


import br.medtec.utils.*;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource extends GenericsResource {

    @Inject
    LoginService loginService;

    @POST
    @Path("login")
    @PermitAll
    public Response login(String json) {
        UsuarioDTO usuarioDTO = JsonUtils.fromJson(json, UsuarioDTO.class);
        if (!loginService.verificaExiste(usuarioDTO, true)){
            return ResponseUtils.badRequest("Email ou Senha Incorreto");
        } else {
            return ResponseUtils.ok(loginService.login(usuarioDTO));
        }

    }

    @POST
    @Path("cadastrar")
    public Response cadastrar(String json) {
        UsuarioDTO usuarioDTO = JsonUtils.fromJson(json, UsuarioDTO.class);
        if (loginService.verificaExiste(usuarioDTO, false)) {
            return ResponseUtils.badRequest("Esse email já está cadastrado");
        } else {
            Usuario usuario = loginService.criaUsuario(usuarioDTO);
            return ResponseUtils.created(usuario);
        }
    }
}
