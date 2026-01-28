package fr.univtln.bruno.demos.docker.domain.model;

/**
 * Domain model representing a Person.
 *
 * @param id        Unique identifier of the person.
 * @param firstname First name of the person.
 * @param lastname  Last name of the person.
 * @param email     Email address of the person.
 */

public record Person(
        Long id,
        String email,
        String firstname,
        String lastname) {
}
