package br.medtec.features.medicamento;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/medicamento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicamentoResource extends GenericsResource {

    @Inject
    MedicamentoService medicamentoService;

    @Inject
    MedicamentoRepository medicamentoRepository;

    @POST
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Cadastrar Medicamento")
    public Response cadastrarMedicamento(MedicamentoDTO medicamentoDTO) {
        try {
            return ResponseUtils.created(medicamentoService.cadastrarMedicamento(medicamentoDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Atualizar Medicamento")
    public Response atualizarMedicamento(MedicamentoDTO medicamentoDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(medicamentoService.atualizarMedicamento(medicamentoDTO, oid));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Deletar Medicamento")
    public Response deletarMedicamento(@PathParam("oid") String oid) {
        try {
            medicamentoService.deletarMedicamento(oid);
            return ResponseUtils.deleted();
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("{oid}")
    @Operation(summary = "Buscar Medicamento")
    @APIResponse(responseCode = "200", description = "Medicamento encontrado", content = @Content(schema = @Schema(implementation = MedicamentoDTO.class)))
    public Response buscarMedicamento(@PathParam("oid") String oid) {
        try {
            if (UtilString.stringValida(oid)) {
                Medicamento medicamento = medicamentoRepository.findByOid(oid);
                medicamento.validarUsuario();
                return ResponseUtils.ok(medicamento);
            } else {
                return ResponseUtils.badRequest("Oid inválido");
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Operation(summary = "Buscar Medicamentos")
    @APIResponse(responseCode = "200", description = "Medicamentos encontrados", content = @Content(schema = @Schema(implementation = MedicamentoDTO.class)))
    public Response buscarMedicamentos(@QueryParam("nome") String nomeMedicamento,
                                       @QueryParam("oidFabricante") String oidFabricante,
                                       @QueryParam("categoria") Integer categoriaMedicamento) {

        try {
            List<Medicamento> medicamentos = medicamentoRepository.findAll(nomeMedicamento, oidFabricante, categoriaMedicamento);
            if (UtilColecao.listaValida(medicamentos)) {
                return ResponseUtils.ok(medicamentos);
            } else {
                return ResponseUtils.badRequest("Nenhum medicamento encontrado");
            }
        }   catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
