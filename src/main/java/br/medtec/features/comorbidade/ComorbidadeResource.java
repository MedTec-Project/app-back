package br.medtec.features.comorbidade;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.ResponseUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("comorbidade")
public class ComorbidadeResource {

    @Inject
    ComorbidadeService comorbidadeService;

    @POST
    public Response cadastrarComorbidade(ComorbidadeDTO comorbidadeDTO) {
        try {
            return ResponseUtils.ok(comorbidadeService.cadastrarComorbidade(comorbidadeDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
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
