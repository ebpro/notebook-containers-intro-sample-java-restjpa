package fr.univtln.bruno.demos.docker.domain.error;

public record InvalidFirstname(String message) implements PersonError {
    @Override
    public String code() {
        return "INVALID_FIRSTNAME";
    }
}
