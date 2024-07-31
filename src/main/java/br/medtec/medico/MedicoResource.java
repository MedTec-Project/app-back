package br.medtec.medico;

import br.medtec.utils.GenericsResource;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.UtilString;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/medicamento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicoResource extends GenericsResource {

    @Inject
    MedicoService medicoService;

    @Inject
    MedicoRepository medicoRepository;

    @POST
    @RolesAllowed({"user", "admin"})
    public Response cadastrar(MedicoDTO medicoDTO) {
        return ResponseUtils.created(medicoService.criarMedico(medicoDTO));
    }

    @PUT
    @RolesAllowed({"user", "admin"})
    public Response atualizar(MedicoDTO medicoDTO) {
        return ResponseUtils.ok(medicoService.atualizarMedico(medicoDTO));
    }

    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    public Response deletar(@PathParam("oid") String oid) {
       try {
            medicoRepository.deleteByOid(oid);
            return ResponseUtils.deleted();
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
       }
    }

    @GET
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    public Response buscar(@PathParam("oid") String oid) {
        if (UtilString.stringValida(oid)) {
            return ResponseUtils.ok(medicoService.buscarMedico(oid));
        } else {
            return ResponseUtils.badRequest("Oid inválido");
        }
    }

    @GET
    @RolesAllowed({"user", "admin"})
    public Response listar(@QueryParam("nome") String nomeMedicamento,
                                       @QueryParam("oidFabricante") String oidFabricante,
                                       @QueryParam("categoria") Integer categoriaMedicamento) {

        List<Medico> medicamentos = medicoRepository.findAll();
        if (medicamentos != null) {
            return ResponseUtils.ok(medicamentos);
        } else {
            return ResponseUtils.badRequest("Nenhum medicamento encontrado");
        }
    }
}
