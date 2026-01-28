package fr.univtln.bruno.demos.docker.domain.error;

public record InvalidLastname(String message) implements PersonError {
    @Override
    public String code() {
        return "INVALID_LASTNAME";
    }
}
