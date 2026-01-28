package fr.univtln.bruno.demos.docker;

import fr.univtln.bruno.demos.docker.adapter.rest.HealthResource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HealthResourceTest {

    @Test
    public void healthReturnsUp() {
        // Use a small stubbed HealthService for unit test simplicity
        fr.univtln.bruno.demos.docker.domain.service.HealthService svc = new fr.univtln.bruno.demos.docker.domain.service.HealthService() {
            @Override
            public boolean dbIsUp() {
                return true;
            }
        };

        HealthResource h = new HealthResource(svc);
        Response r = h.health();
        assertEquals(200, r.getStatus());
        Object entity = r.getEntity();
        assertTrue(entity instanceof Map);
        Map<?, ?> map = (Map<?, ?>) entity;
        assertEquals("UP", map.get("status"));
        assertEquals("UP", map.get("db"));
    }
}
