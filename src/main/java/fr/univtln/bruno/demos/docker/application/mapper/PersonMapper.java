package fr.univtln.bruno.demos.docker.application.mapper;

import fr.univtln.bruno.demos.docker.application.dto.PersonDto;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface PersonMapper {

    PersonDto domainToDto(Person domain);

    Person dtoToDomain(PersonDto dto);
}
