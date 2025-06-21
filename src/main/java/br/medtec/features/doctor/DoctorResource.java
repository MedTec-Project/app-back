package br.medtec.features.doctor;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.GenericsResource;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.StringUtil;
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

@Path("/doctor")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DoctorResource extends GenericsResource {

    @Inject
    DoctorService doctorService;

    @Inject
    DoctorRepository doctorRepository;

    @POST
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Register Doctor")
    public Response register(DoctorDTO doctorDTO) {
        try {
            return ResponseUtils.created(doctorService.createDoctor(doctorDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Update Doctor")
    public Response update(DoctorDTO doctorDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(doctorService.updateDoctor(doctorDTO, oid));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Delete Doctor")
    public Response delete(@PathParam("oid") String oid) {
        try {
            doctorService.deleteDoctor(oid);
            return ResponseUtils.deleted();
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Find Doctor")
    @APIResponse(responseCode = "200", description = "Doctor found", content = @Content(schema = @Schema(implementation = DoctorDTO.class)))
    public Response find(@PathParam("oid") String oid) {
        try {
            if (StringUtil.isValidString(oid)) {
                return ResponseUtils.ok(doctorService.findDoctor(oid));
            } else {
                return ResponseUtils.badRequest("Médico não encontrado");
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "List Doctors")
    public Response list() {
        try {
            List<Doctor> doctors = doctorRepository.findAll();
            if (doctors != null) {
                return ResponseUtils.ok(doctors);
            } else {
                return ResponseUtils.ok("Nenhum médico encontrado");
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
