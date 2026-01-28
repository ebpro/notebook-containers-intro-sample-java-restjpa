package fr.univtln.bruno.demos.docker.application.usecase;

import fr.univtln.bruno.demos.docker.application.dto.CreatePersonRequest;
import fr.univtln.bruno.demos.docker.application.dto.PersonDto;
import fr.univtln.bruno.demos.docker.application.mapper.PersonDtoMapper;
import fr.univtln.bruno.demos.docker.domain.port.PersonWritePort;
import fr.univtln.bruno.demos.docker.domain.error.PersonError;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import fr.univtln.bruno.demos.docker.domain.validation.PersonFactory;

public class CreatePersonUseCase {

    private final PersonWritePort repo;
    private final PersonDtoMapper mapper;

    @Inject
    public CreatePersonUseCase(PersonWritePort repo, PersonDtoMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public Either<PersonError, PersonDto> execute(CreatePersonRequest req) {
        return PersonFactory.create(null, req.email(), req.firstname(), req.lastname())
                .map(repo::save)
                .map(mapper::toDto);
    }
}
