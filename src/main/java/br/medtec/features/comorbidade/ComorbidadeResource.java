package br.medtec.features.comorbidade;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("comorbidade")
public class ComorbidadeResource {

    @POST
    public Response cadastrarComorbidade(Comorbidade comorbidade) {
        return Response.ok().build();
    }

    @PUT
    public Response atualizarComorbidade(Comorbidade comorbidade) {
        return Response.ok().build();
    }


    @GET
    public Response listarComorbidades() {
        return Response.ok().build();
    }

    @GET
    @Path("{oid}")
    public Response buscarComorbidadePorId(String oid) {
        return Response.ok().build();
    }
}
