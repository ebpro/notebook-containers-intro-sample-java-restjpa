package fr.univtln.bruno.demos.docker;

import fr.univtln.bruno.demos.docker.adapter.rest.ErrorResponse;
import fr.univtln.bruno.demos.docker.adapter.rest.GlobalExceptionMapper;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionMapperTest {

    @Test
    public void mapsExceptionToErrorResponse() {
        GlobalExceptionMapper mapper = new GlobalExceptionMapper();
        RuntimeException ex = new RuntimeException("boom");
        Response r = mapper.toResponse(ex);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), r.getStatus());
        assertNotNull(r.getMediaType());
        ErrorResponse err = (ErrorResponse) r.getEntity();
        assertNotNull(err);
        assertEquals("INTERNAL_ERROR", err.getCode());
        assertEquals("Unexpected error", err.getMessage());
        assertEquals("boom", err.getDetails());
    }
}
