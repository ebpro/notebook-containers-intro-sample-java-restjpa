package fr.univtln.bruno.demos.docker.domain.validation;

import fr.univtln.bruno.demos.docker.domain.error.PersonError;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import io.vavr.control.Either;

public final class PersonFactory {

    private PersonFactory() {
    }

    public static Either<PersonError, Person> create(
            Long id,
            String email,
            String firstname,
            String lastname) {
        return PersonValidator.validate(email, firstname, lastname)
                .map(v -> new Person(id, email, firstname, lastname));
    }
}
