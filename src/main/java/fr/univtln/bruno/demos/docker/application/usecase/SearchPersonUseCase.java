package fr.univtln.bruno.demos.docker.application.usecase;

import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;
import fr.univtln.bruno.demos.docker.application.pagination.PageResult;
import fr.univtln.bruno.demos.docker.application.pagination.SortRequest;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import fr.univtln.bruno.demos.docker.domain.port.PersonReadPort;
import jakarta.inject.Inject;

import java.util.List;

public class SearchPersonUseCase {

    private final PersonReadPort repo;

    @Inject
    public SearchPersonUseCase(PersonReadPort repo) {
        this.repo = repo;
    }

    public PageResult<Person> execute(PageRequest pageRequest, List<SortRequest> sort) {
        return repo.findAll(pageRequest, sort);
    }

}
