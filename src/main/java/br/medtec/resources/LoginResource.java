package br.medtec.resources;


import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.services.LoginService;
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

    @POST
    @Path("login")
    public Response login(UsuarioDTO usuarioDTO) {
        if (!loginService.verificaExiste(usuarioDTO)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            return Response.status(Response.Status.OK).entity(new Gson().toJson(usuarioDTO)).build();
        }

    }

    @Path("cadastrar")
    public Response cadastrar(UsuarioDTO usuarioDTO) {
        if (loginService.verificaExiste(usuarioDTO)){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            Usuario usuario = loginService.criaUsuario(usuarioDTO);
            return Response.status(Response.Status.OK).entity(new Gson().toJson(usuario)).build();
        }
    }

}
