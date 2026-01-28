package fr.univtln.bruno.demos.docker.application.usecase;

import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;
import fr.univtln.bruno.demos.docker.application.pagination.PageResult;
import fr.univtln.bruno.demos.docker.application.pagination.SortRequest;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import fr.univtln.bruno.demos.docker.domain.port.PersonReadPort;
import jakarta.inject.Inject;

import java.util.List;

public class GetPersonsUseCase {

    private final PersonReadPort readPort;

    @Inject
    public GetPersonsUseCase(PersonReadPort readPort) {
        this.readPort = readPort;
    }

    public PageResult<Person> execute(PageRequest request, List<SortRequest> sortBy) {
        return readPort.findAll(request, sortBy);
    }

}
