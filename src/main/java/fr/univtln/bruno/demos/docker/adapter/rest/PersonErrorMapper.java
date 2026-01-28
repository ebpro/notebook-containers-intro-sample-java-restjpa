package fr.univtln.bruno.demos.docker.adapter.rest;

import fr.univtln.bruno.demos.docker.domain.error.PersonError;
import jakarta.ws.rs.core.Response;

public final class PersonErrorMapper {

    private PersonErrorMapper() {
    }

    public static Response.Status toStatus(PersonError err) {
        return switch (err.code()) {
            case "NOT_FOUND" -> Response.Status.NOT_FOUND;
            default -> Response.Status.BAD_REQUEST;
        };
    }
}
