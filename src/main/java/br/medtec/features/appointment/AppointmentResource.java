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


@Path("appointment")
public class AppointmentResource {

    final AppointmentRepository appointmentRepository;

    @Inject
    AppointmentService appointmentService;

    @Inject
    public AppointmentResource(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

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
                return ResponseUtils.badRequest("Consulta não encontrada");
            }
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "List Appointments")
    @Produces("application/json")
    public Response list() {
        try {
            return ResponseUtils.ok(appointmentRepository.findAll());
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("today")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Get Today Appointments")
    @Produces("application/json")
    public Response findAppointmentsToday() {
        try {
            return ResponseUtils.ok(appointmentRepository.findAppointmentsToday());
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}/mark")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Mark Appointment Done")
    public Response markAppointmentDone(@PathParam("oid") String oid, AppointmentDoneDTO appointmentDoneDTO) {
        try {
            appointmentService.markAppointmentDone(oid, appointmentDoneDTO);
            return ResponseUtils.ok("Appointment marked as done successfully.");
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

}
