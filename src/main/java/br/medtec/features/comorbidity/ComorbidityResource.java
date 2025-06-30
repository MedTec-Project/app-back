package br.medtec.features.comorbidity;

import br.medtec.utils.ResponseUtils;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("comorbidity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ComorbidityResource {

    private final ComorbidityService comorbidityService;

    private final ComorbidityRepository comorbidityRepository;


    @Inject
    public ComorbidityResource(ComorbidityService comorbidityService, ComorbidityRepository comorbidityRepository) {
        this.comorbidityService = comorbidityService;
        this.comorbidityRepository = comorbidityRepository;
    }


    @POST
    @RolesAllowed({"user", "admin"})
    public Response registerComorbidity(ComorbidityDTO comorbidityDTO) {
        try {
            return ResponseUtils.created(comorbidityService.registerComorbidity(comorbidityDTO));
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @PUT
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    public Response updateComorbidity(ComorbidityDTO comorbidityDTO, @PathParam("oid") String oid) {
        try {
            return ResponseUtils.ok(comorbidityService.updateComorbidity(comorbidityDTO, oid));
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"user", "admin"})
    public Response getComorbidity() {
        try {
            return ResponseUtils.ok(comorbidityService.getComorbidity());
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @DELETE
    @Path("{oid}")
    @RolesAllowed({"user", "admin"})
    public Response deleteComorbidity(@PathParam("oid") String oid) {
        try {
            comorbidityService.deleteComorbidity(oid);
            return ResponseUtils.deleted();
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }

    @GET
    @Path("types")
    @RolesAllowed({"user", "admin"})
    public Response getComorbidityTypes() {
        try {
            return ResponseUtils.ok(comorbidityRepository.findAllTypes());
        } catch (Exception e) {
            return ResponseUtils.badRequest(e.getMessage());
        }
    }
}
