package fr.univtln.bruno.demos.docker.application.dto;

/**
 * Data Transfer Object for Person entity.
 * Holds only the fields to be exposed via REST API.
 * Used in request and response bodies.
 *
 * @param id        Unique identifier of the person.
 * @param email     Email address of the person.
 * @param firstname First name of the person.
 * @param lastname  Last name of the person.
 *
 * @see fr.univtln.bruno.demos.docker.domain.model.Person
 */
public record PersonDto(Long id, String email, String firstname, String lastname) {
}
