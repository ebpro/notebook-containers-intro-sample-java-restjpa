package fr.univtln.bruno.demos.docker.adapter.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Global exception mapper that converts unhandled exceptions into standardized
 * error responses.
 * This ensures that clients receive consistent error information in JSON
 * format.
 * When an exception occurs, it is mapped to a 500 Internal Server Error
 * response
 * with a structured ErrorResponse entity.
 * The error response includes a code, message, and details about the exception.
 *
 * This class is annotated with @Provider to be automatically discovered by
 * JAX-RS.
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        ErrorResponse error = new ErrorResponse(
                "INTERNAL_ERROR",
                "Unexpected error",
                exception.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
