package br.medtec.features.schedule;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.schedule.schedulelog.ScheduleLogDTO;
import br.medtec.utils.ResponseUtils;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("schedule")
public class ScheduleResource {

    @Inject
    ScheduleService scheduleService;

    @Inject
    ScheduleRepository scheduleRepository;

    @POST
    @Operation(summary = "Register Schedule")
    @RolesAllowed({"user", "admin"})
    public Response registerSchedule(ScheduleDTO scheduleDTO) {
        try {
            return ResponseUtils.created(scheduleService.registerSchedule(scheduleDTO));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Update Schedule")
    public Response updateSchedule(ScheduleDTO scheduleDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(scheduleService.updateSchedule(scheduleDTO, oid));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Delete Schedule")
    public Response deleteSchedule(@PathParam("oid") String oid) {
        try {
            scheduleService.deleteSchedule(oid);
            return ResponseUtils.deleted();
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("{oid}")
    @Operation(summary = "Find Schedule")
    @APIResponse(responseCode = "200", description = "Schedule found", content = @Content(schema = @Schema(implementation = ScheduleDTO.class)))
    public Response findSchedule(@PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(scheduleService.getSchedule(oid));
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("today")
    @Operation(summary = "Get Today Schedules")
    @APIResponse(responseCode = "200", description = "Schedules found", content = @Content(schema = @Schema(implementation = ScheduleDTO.class)))
    public Response findSchedulesToday() {
        try {
            List<ScheduleLogDTO> schedules = scheduleService.getSchedulesToday();
            return ResponseUtils.ok(schedules);
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("general")
    @Operation(summary = "Get All Schedules")
    @APIResponse(responseCode = "200", description = "Schedules found", content = @Content(schema = @Schema(implementation = ScheduleDTO.class)))
    public Response findSchedulesGeneral() {
        try {
            List<ScheduleLogDTO> schedules = scheduleService.getSchedulesGeneral();
            return ResponseUtils.ok(schedules);
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}/mark")
    @RolesAllowed({"user", "admin"})
    @Operation(summary = "Mark Schedule Taken")
    public Response markScheduleTaken(@PathParam("oid") String oid, ScheduleTakenDTO scheduleTakenDTO) {
        try {
            scheduleService.markScheduleTaken(oid, scheduleTakenDTO.getTaken());
            return ResponseUtils.ok("Schedule marked as taken successfully.");
        } catch (MEDBadRequestExecption e) {
            return ResponseUtils.badRequest(e.getMessage());
        }

    }
}
