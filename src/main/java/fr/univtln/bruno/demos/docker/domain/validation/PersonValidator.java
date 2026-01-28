package fr.univtln.bruno.demos.docker.domain.validation;

import fr.univtln.bruno.demos.docker.domain.error.InvalidEmail;
import fr.univtln.bruno.demos.docker.domain.error.InvalidFirstname;
import fr.univtln.bruno.demos.docker.domain.error.InvalidLastname;
import fr.univtln.bruno.demos.docker.domain.error.PersonError;
import io.vavr.control.Either;

public final class PersonValidator {

    private static final String EMAIL_REGEX = ".+@.+\\..+";

    private PersonValidator() {
    }

    public static Either<PersonError, Void> validate(
            String email,
            String firstname,
            String lastname) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            return Either.left(new InvalidEmail("Invalid email"));
        }
        if (firstname == null || firstname.isBlank()) {
            return Either.left(new InvalidFirstname("Firstname is required"));
        }
        if (lastname == null || lastname.isBlank()) {
            return Either.left(new InvalidLastname("Lastname is required"));
        }
        return Either.right(null);
    }
}
