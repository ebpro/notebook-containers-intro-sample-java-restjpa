package fr.univtln.bruno.demos.docker.domain.error;

public sealed interface PersonError permits InvalidEmail, InvalidFirstname, InvalidLastname {
    String code();

    String message();
}
