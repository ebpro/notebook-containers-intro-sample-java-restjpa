package fr.univtln.bruno.demos.docker;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonEndpointIT {

    private HttpServer server;
    private Client client;

    @Path("/persons")
    public static class TestPersonResource {
        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public jakarta.ws.rs.core.Response list() {
            List<fr.univtln.bruno.demos.docker.application.dto.PersonDto> list = Arrays.asList(
                    new fr.univtln.bruno.demos.docker.application.dto.PersonDto(1L, "alice@example.com", "Alice", ""),
                    new fr.univtln.bruno.demos.docker.application.dto.PersonDto(2L, "bob@example.com", "Bob", ""));
            return jakarta.ws.rs.core.Response.ok(list).build();
        }
    }

    @BeforeEach
    public void startServer() throws Exception {
        ResourceConfig rc = new ResourceConfig().register(TestPersonResource.class);
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://0.0.0.0:9998/"), rc, false);
        server.start();
        client = JerseyClientBuilder.createClient();
    }

    @AfterEach
    public void stopServer() {
        if (server != null)
            server.shutdownNow();
        if (client != null)
            client.close();
    }

    @Test
    public void persons_endpoint_returns_list() {
        WebTarget target = client.target("http://localhost:9998/").path("persons");
        jakarta.ws.rs.core.Response r = target.request(MediaType.APPLICATION_JSON).get();
        assertEquals(200, r.getStatus());
        fr.univtln.bruno.demos.docker.application.dto.PersonDto[] dtos = r
                .readEntity(fr.univtln.bruno.demos.docker.application.dto.PersonDto[].class);
        assertNotNull(dtos);
        assertEquals(2, dtos.length);
        assertEquals("Alice", dtos[0].firstname());
        assertEquals("Bob", dtos[1].firstname());
    }
}
