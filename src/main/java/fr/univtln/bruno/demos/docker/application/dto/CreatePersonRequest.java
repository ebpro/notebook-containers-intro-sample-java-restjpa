package fr.univtln.bruno.demos.docker.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreatePersonRequest(
        @NotBlank @Email String email,
        @NotBlank String firstname,
        @NotBlank String lastname) {
}
