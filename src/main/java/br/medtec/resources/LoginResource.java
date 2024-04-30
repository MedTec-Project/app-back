package br.medtec.resources;


import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.services.LoginService;
import br.medtec.utils.*;
import com.google.gson.Gson;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    LoginService loginService;


    @POST
    @Path("login")
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
        if (loginService.verificaExiste(usuarioDTO, false)){
            return ResponseUtils.badRequest("Esse email já está cadastrado");
        } else {
            Usuario usuario = loginService.criaUsuario(usuarioDTO);
            return ResponseUtils.created(usuario);
        }
    }

}
