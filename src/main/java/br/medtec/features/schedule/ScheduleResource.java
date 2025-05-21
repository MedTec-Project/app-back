package br.medtec.features.schedule;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.image.ImageService;
import br.medtec.features.schedule.Schedule;
import br.medtec.features.schedule.ScheduleDTO;
import br.medtec.features.schedule.ScheduleRepository;
import br.medtec.features.schedule.ScheduleService;
import br.medtec.utils.ResponseUtils;
import br.medtec.utils.StringUtil;
import br.medtec.utils.UtilCollection;
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


}
