package br.medtec.features.appointment;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.StringUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("appointments")
public class AppointmentResource {

    @Inject
    AppointmentService appointmentService;

    @POST
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Register Appointment")
    @Produces("application/json")
    @Consumes("application/json")
    public Response register(AppointmentDTO appointmentDTO) {
        try {
            return ResponseUtils.created(appointmentService.createAppointment(appointmentDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Update Appointment")
    @Produces("application/json")
    @Consumes("application/json")
    public Response update(AppointmentDTO appointmentDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(appointmentService.updateAppointment(appointmentDTO, oid));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Delete Appointment")
    @Produces("application/json")
    public Response delete(@PathParam("oid") String oid) {
        try {
            appointmentService.deleteAppointment(oid);
            return ResponseUtils.deleted();
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Find Appointment")
    @Produces("application/json")
    @APIResponse(responseCode = "200", description = "Appointment found", content = @Content(schema = @Schema(implementation = AppointmentDTO.class)))
    public Response find(@PathParam("oid") String oid) {
        try {
            if (StringUtil.isValidString(oid)) {
                return ResponseUtils.ok(appointmentService.findAppointment(oid));
            } else {
                return ResponseUtils.badRequest("Médico não encontrado");
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

}
