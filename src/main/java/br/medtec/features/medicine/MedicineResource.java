package br.medtec.features.medicine;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.image.ImageService;
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

@Path("/medicine")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicineResource extends GenericsResource {

    @Inject
    MedicineService medicineService;

    @Inject
    MedicineRepository medicineRepository;

    @Inject
    ImageService imageService;

    @POST
    @Operation(summary = "Register Medicine")
    @RolesAllowed({"user", "admin"})
    public Response registerMedicine(MedicineDTO medicineDTO) {
        try {
            return ResponseUtils.created(medicineService.registerMedicine(medicineDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Update Medicine")
    public Response updateMedicine(MedicineDTO medicineDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(medicineService.updateMedicine(medicineDTO, oid));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Delete Medicine")
    public Response deleteMedicine(@PathParam("oid") String oid) {
        try {
            medicineService.deleteMedicine(oid);
            return ResponseUtils.deleted();
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("{oid}")
    @Operation(summary = "Find Medicine")
    @APIResponse(responseCode = "200", description = "Medicine found", content = @Content(schema = @Schema(implementation = MedicineDTO.class)))
    public Response findMedicine(@PathParam("oid") String oid) {
        try {
            if (StringUtil.isValidString(oid)) {
                Medicine medicine = medicineRepository.findByOid(oid);
                medicine.validateUser();
                medicine.setImageBase64(imageService.getImage(medicine.getImagePath()));
                return ResponseUtils.ok(medicine);
            } else {
                return ResponseUtils.badRequest("Oid inválido");
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Operation(summary = "Find Medicines")
    @APIResponse(responseCode = "200", description = "Medicines found", content = @Content(schema = @Schema(implementation = MedicineDTO.class)))
    public Response findMedicines(@QueryParam("name") String medicineName,
                                  @QueryParam("oidManufacturer") String oidManufacturer,
                                  @QueryParam("category") Integer medicineCategory) {

        try {
            List<MedicineDTO> medicines = medicineRepository.findAll(medicineName, oidManufacturer, medicineCategory);
            if (UtilCollection.isValidList(medicines)) {
                medicines.forEach(medicine -> medicine.setImageBase64(imageService.getImage(medicine.getImagePath())));
                return ResponseUtils.ok(medicines);
            } else {
                return ResponseUtils.badRequest("Nenhum medicamento encontrado");
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
