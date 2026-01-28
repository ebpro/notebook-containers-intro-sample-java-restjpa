package fr.univtln.bruno.demos.docker.domain.port;

import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;
import fr.univtln.bruno.demos.docker.application.pagination.PageResult;
import fr.univtln.bruno.demos.docker.application.pagination.SortRequest;
import fr.univtln.bruno.demos.docker.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonReadPort extends ReadPort<Person, Long> {

        Optional<Person> findByEmail(String email);

        PageResult<Person> findAll(PageRequest request, List<SortRequest> sortBy);

        PageResult<Person> searchByFirstname(String firstname, PageRequest request, List<SortRequest> sortBy);

        PageResult<Person> searchByLastname(String lastname, PageRequest request, List<SortRequest> sortBy);

}
