package br.medtec.features.calendar;

import br.medtec.utils.ResponseUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("calendar")
public class CalendarResource {

    @Inject
    CalendarService calendarService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCalendar() {
        try {
            return ResponseUtils.ok(calendarService.getEvents());
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEvent(RegisterEventDTO eventDTO) {
        try {
            return ResponseUtils.created(calendarService.createEvent(eventDTO));
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEvent(RegisterEventDTO eventDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(calendarService.updateEvent(eventDTO, oid));
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }


    @GET
    @Path("{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(calendarService.findEvent(oid));
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }


    @DELETE
    @Path("{oid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEvent(@PathParam("oid") String oid) {
        try {
            calendarService.deleteEvent(oid);
            return ResponseUtils.deleted();
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
