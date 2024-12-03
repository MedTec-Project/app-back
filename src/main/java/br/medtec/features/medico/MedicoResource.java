package br.medtec.features.medico;

import br.medtec.utils.GenericsResource;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.UtilString;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/medico")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicoResource extends GenericsResource {

    @Inject
    MedicoService medicoService;

    @Inject
    MedicoRepository medicoRepository;

    @POST
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Cadastrar Medico")
    public Response cadastrar(MedicoDTO medicoDTO) {
        try {
            return ResponseUtils.created(medicoService.criarMedico(medicoDTO));
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Atualizar Medico")
    public Response atualizar(MedicoDTO medicoDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(medicoService.atualizarMedico(medicoDTO, oid));
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Deletar Medico")
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
    @Operation(summary = "Buscar Medico")
    @APIResponse(responseCode = "200", description = "Medicamento encontrado", content = @Content(schema = @Schema(implementation = MedicoDTO.class)))
    public Response buscar(@PathParam("oid") String oid) {
        if (UtilString.stringValida(oid)) {
            return ResponseUtils.ok(medicoService.buscarMedico(oid));
        } else {
            return ResponseUtils.badRequest("Medicamento não encontrado");
        }
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Listar Medico")
    public Response listar(@QueryParam("nome") String nomeMedicamento,
                                       @QueryParam("oidFabricante") String oidFabricante,
                                       @QueryParam("categoria") Integer categoriaMedicamento) {

        List<Medico> medicamentos = medicoRepository.findAll();
        if (medicamentos != null) {
            return ResponseUtils.ok(medicamentos);
        } else {
            return ResponseUtils.badRequest("Nenhum medico encontrado");
        }
    }
}
