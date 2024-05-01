package br.medtec.resources;

import br.medtec.dto.MedicamentoDTO;
import br.medtec.entity.Medicamento;
import br.medtec.services.MedicamentoService;
import br.medtec.utils.JsonUtils;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.UtilString;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/medicamento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicamentoResource {

    @Inject
    MedicamentoService medicamentoService;

    @POST
    public void cadastrarMedicamento(String json) {
        MedicamentoDTO medicamentoDTO = JsonUtils.fromJson(json, MedicamentoDTO.class);
        medicamentoService.cadastrarMedicamento(medicamentoDTO);
        ResponseUtils.created(medicamentoDTO);
    }

    @PUT
    public void atualizarMedicamento(String json) {
        MedicamentoDTO medicamentoDTO = JsonUtils.fromJson(json, MedicamentoDTO.class);
        medicamentoService.atualizarMedicamento(medicamentoDTO);
        ResponseUtils.ok(medicamentoDTO);
    }

    @DELETE
    @Path("{oid}")
    public void deletarMedicamento(@PathParam("oid") String oid) {
        medicamentoService.deletarMedicamento(oid);
        ResponseUtils.deleted();
    }

    @GET
    @Path("{oid}")
    public void buscarMedicamento(@PathParam("oid") String oid) {
        if (UtilString.stringValida(oid)) {
            ResponseUtils.ok(medicamentoService.buscarMedicamento(oid));
        } else {
            ResponseUtils.badRequest("Oid inválido");
        }
    }

    @GET
    public void buscarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoService.buscarMedicamentos();
        if (medicamentos != null) {
            ResponseUtils.ok(medicamentos);
        } else {
            ResponseUtils.badRequest("Nenhum medicamento encontrado");
        }
    }
}
