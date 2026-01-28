package fr.univtln.bruno.demos.docker.application.mapper;

import fr.univtln.bruno.demos.docker.application.dto.PersonDto;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import org.mapstruct.factory.Mappers;
import fr.univtln.bruno.demos.docker.application.mapper.PersonMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonMapperTest {

    @Test
    public void toDto_null() {
        var mapper = Mappers.getMapper(PersonMapper.class);
        assertNull(mapper.domainToDto(null));
    }

    @Test
    public void toDto_and_toDomain_roundtrip() {
        var mapper = Mappers.getMapper(PersonMapper.class);
        Person p = new Person(1L, "alice@example.com", "Alice", "");
        PersonDto dto = mapper.domainToDto(p);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals("Alice", dto.firstname());

        Person back = mapper.dtoToDomain(dto);
        assertNotNull(back);
        assertEquals(p.id(), back.id());
        assertEquals(p.firstname(), back.firstname());
    }
}
