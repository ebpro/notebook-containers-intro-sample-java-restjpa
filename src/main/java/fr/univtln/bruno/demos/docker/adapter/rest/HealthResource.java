package fr.univtln.bruno.demos.docker.adapter.rest;

import fr.univtln.bruno.demos.docker.application.dto.HealthStatus;
import fr.univtln.bruno.demos.docker.domain.service.HealthService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    private final HealthService healthService;

    @Inject
    public HealthResource(HealthService healthService) {
        this.healthService = healthService;
    }

    @GET
    public Response health() {
        boolean dbUp = healthService.dbIsUp();

        if (dbUp) {
            return Response.ok(new HealthStatus("UP", "UP")).build();
        }

        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(new HealthStatus("DOWN", "DOWN"))
                .build();
    }
}
