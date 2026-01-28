package fr.univtln.bruno.demos.docker.adapter.persistence;

import fr.univtln.bruno.demos.docker.domain.model.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface PersonPersistenceMapper {

    PersonEntity toEntity(Person person);

    Person toDomain(PersonEntity entity);
}
