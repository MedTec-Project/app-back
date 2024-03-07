package br.medtec.resources;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/login")
public class Login {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String login(String json) {
        return "oi";
    }

}
