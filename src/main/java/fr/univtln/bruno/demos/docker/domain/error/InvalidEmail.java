package fr.univtln.bruno.demos.docker.domain.error;

public record InvalidEmail(String message) implements PersonError {
    @Override
    public String code() {
        return "INVALID_EMAIL";
    }
}
