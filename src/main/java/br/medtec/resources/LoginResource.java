package br.medtec.resources;


import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.services.LoginService;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.Validacao;
import br.medtec.utils.Validcoes;
import com.google.gson.Gson;
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

    @Inject
    Gson gson;

    @POST
    @Path("login")
    public Response login(String json) {
        UsuarioDTO usuarioDTO = gson.fromJson(json, UsuarioDTO.class);
        if (!loginService.verificaExiste(usuarioDTO)){
            return ResponseUtils.badRequest(new Validacao("Email ou Senha Incorreto"));
        } else {
            return ResponseUtils.ok(usuarioDTO);
        }

    }

    @Path("cadastrar")
    public Response cadastrar(UsuarioDTO usuarioDTO) {
        if (loginService.verificaExiste(usuarioDTO)){
            return ResponseUtils.badRequest(new Validacao("Esse email já está cadastrado"));
        } else {
            Usuario usuario = loginService.criaUsuario(usuarioDTO);
            return ResponseUtils.created(usuarioDTO);
        }
    }

}
