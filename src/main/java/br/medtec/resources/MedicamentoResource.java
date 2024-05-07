package br.medtec.resources;

import br.medtec.dto.MedicamentoDTO;
import br.medtec.entity.Medicamento;
import br.medtec.repositories.MedicamentoRepository;
import br.medtec.services.MedicamentoService;
import br.medtec.utils.JsonUtils;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.UtilString;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/medicamento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicamentoResource {

    @Inject
    MedicamentoService medicamentoService;

    @Inject
    MedicamentoRepository medicamentoRepository;

    @POST
    public Response cadastrarMedicamento(String json) {
        MedicamentoDTO medicamentoDTO = JsonUtils.fromJson(json, MedicamentoDTO.class);
        medicamentoService.cadastrarMedicamento(medicamentoDTO);
        return ResponseUtils.created(medicamentoDTO);
    }

    @PUT
    public Response atualizarMedicamento(String json) {
        MedicamentoDTO medicamentoDTO = JsonUtils.fromJson(json, MedicamentoDTO.class);
        medicamentoService.atualizarMedicamento(medicamentoDTO);
        return ResponseUtils.ok(medicamentoDTO);
    }

    @DELETE
    @Path("{oid}")
    public Response deletarMedicamento(@PathParam("oid") String oid) {
        medicamentoService.deletarMedicamento(oid);
        return ResponseUtils.deleted();
    }

    @GET
    @Path("{oid}")
    public Response buscarMedicamento(@PathParam("oid") String oid) {
        if (UtilString.stringValida(oid)) {
            return ResponseUtils.ok(medicamentoRepository.findByOid(oid));
        } else {
            return ResponseUtils.badRequest("Oid inválido");
        }
    }

    @GET
    public Response buscarMedicamentos(@QueryParam("nome") String nomeMedicamento,
                                       @QueryParam("oidFabricante") String oidFabricante,
                                       @QueryParam("categoria") Integer categoriaMedicamento) {

        List<Medicamento> medicamentos = medicamentoRepository.findAll(nomeMedicamento, oidFabricante, categoriaMedicamento);
        if (medicamentos != null) {
           return ResponseUtils.ok(medicamentos);
        } else {
            return ResponseUtils.badRequest("Nenhum medicamento encontrado");
        }
    }
}
