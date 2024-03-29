package br.medtec.resources;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/login")
public class LoginResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String login(String json) {

        return json;
    }

}
