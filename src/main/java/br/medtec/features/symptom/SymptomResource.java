package br.medtec.features.symptom;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("symptom")
public class SymptomResource {

    @Inject
    JpaSymptomRepository repository;

    @GET
    public Response listSymptoms() {
        return Response.ok(repository.findAll()).build();
    }
}
