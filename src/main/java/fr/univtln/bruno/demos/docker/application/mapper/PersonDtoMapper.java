package fr.univtln.bruno.demos.docker.application.mapper;

import fr.univtln.bruno.demos.docker.application.dto.CreatePersonRequest;
import fr.univtln.bruno.demos.docker.application.dto.PersonDto;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta")
public interface PersonDtoMapper {
    // Standard singleton for SE environments
    PersonDtoMapper INSTANCE = Mappers.getMapper(PersonDtoMapper.class);

    @Mapping(target = "id", ignore = true)
    Person toDomain(CreatePersonRequest request);

    PersonDto toDto(Person person);

    Person toDomain(PersonDto dto);
}
