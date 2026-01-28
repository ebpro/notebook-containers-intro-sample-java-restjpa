package fr.univtln.bruno.demos.docker.domain.service;

import fr.univtln.bruno.demos.docker.domain.error.PersonError;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import fr.univtln.bruno.demos.docker.domain.validation.PersonFactory;
import io.vavr.control.Either;

public final class PersonService {

    private PersonService() {
    }

    public static Either<PersonError, Person> create(
            String email,
            String firstname,
            String lastname) {

        return PersonFactory.create(null, email, firstname, lastname);
    }

    public static Either<PersonError, Person> updateEmail(Person person, String email) {
        return PersonFactory.create(person.id(), email, person.firstname(), person.lastname());
    }

    public static Either<PersonError, Person> updateFirstname(Person person, String firstname) {
        return PersonFactory.create(person.id(), person.email(), firstname, person.lastname());
    }

    public static Either<PersonError, Person> updateLastname(Person person, String lastname) {
        return PersonFactory.create(person.id(), person.email(), person.firstname(), lastname);
    }
}
