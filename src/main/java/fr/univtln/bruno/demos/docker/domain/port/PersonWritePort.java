package fr.univtln.bruno.demos.docker.domain.port;

import fr.univtln.bruno.demos.docker.domain.model.Person;

public interface PersonWritePort {

    Person save(Person person);

    void deleteById(Long id);
}
