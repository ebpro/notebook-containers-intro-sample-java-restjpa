package fr.univtln.bruno.demos.docker.application.command;

public record CreatePersonCommand(
        String email,
        String firstname,
        String lastname) {
}
