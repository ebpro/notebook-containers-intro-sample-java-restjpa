package fr.univtln.bruno.demos.docker.application.usecase;

import fr.univtln.bruno.demos.docker.application.dto.PersonDto;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import fr.univtln.bruno.demos.docker.domain.port.PersonReadPort;
import org.junit.jupiter.api.Test;

import java.util.List;
import fr.univtln.bruno.demos.docker.application.pagination.PageResult;

import static org.junit.jupiter.api.Assertions.*;

public class GetPersonsTest {

    @Test
    public void execute_returns_mapped_dtos() {

        PersonReadPort stubRepo = new PersonReadPort() {
            final java.util.List<Person> store = java.util.List.of(
                    new Person(1L, "alice@example.com", "Alice", ""),
                    new Person(2L, "bob@example.com", "Bob", ""));

            @Override
            public java.util.Optional<Person> findById(Long id) {
                return store.stream().filter(p -> p.id().equals(id)).findFirst();
            }

            @Override
            public fr.univtln.bruno.demos.docker.application.pagination.PageResult<Person> findAll(
                    fr.univtln.bruno.demos.docker.application.pagination.PageRequest request,
                    java.util.List<fr.univtln.bruno.demos.docker.application.pagination.SortRequest> sort) {
                int page = request.page();
                int size = request.size();
                long total = store.size();
                int from = Math.min(store.size(), page * size);
                int to = Math.min(store.size(), from + size);
                java.util.List<Person> slice = store.subList(from, to);
                return new fr.univtln.bruno.demos.docker.application.pagination.PageResult<>(slice, page, size, total);
            }

            @Override
            public fr.univtln.bruno.demos.docker.application.pagination.PageResult<Person> searchByLastname(
                    String lastname, fr.univtln.bruno.demos.docker.application.pagination.PageRequest request,
                    java.util.List<fr.univtln.bruno.demos.docker.application.pagination.SortRequest> sort) {
                // simple stub: filter by lastname equality
                var filtered = store.stream().filter(p -> p.lastname().equals(lastname)).toList();
                int page = request.page();
                int size = request.size();
                long total = filtered.size();
                int from = Math.min(filtered.size(), page * size);
                int to = Math.min(filtered.size(), from + size);
                var slice = filtered.subList(from, to);
                return new fr.univtln.bruno.demos.docker.application.pagination.PageResult<>(slice, page, size, total);
            }

            @Override
            public fr.univtln.bruno.demos.docker.application.pagination.PageResult<Person> searchByFirstname(
                    String firstname, fr.univtln.bruno.demos.docker.application.pagination.PageRequest request,
                    java.util.List<fr.univtln.bruno.demos.docker.application.pagination.SortRequest> sort) {
                var filtered = store.stream().filter(p -> p.firstname().equals(firstname)).toList();
                int page = request.page();
                int size = request.size();
                long total = filtered.size();
                int from = Math.min(filtered.size(), page * size);
                int to = Math.min(filtered.size(), from + size);
                var slice = filtered.subList(from, to);
                return new fr.univtln.bruno.demos.docker.application.pagination.PageResult<>(slice, page, size, total);
            }

            @Override
            public long count() {
                return store.size();
            }

            @Override
            public boolean existsById(Long id) {
                return store.stream().anyMatch(p -> p.id().equals(id));
            }

            @Override
            public java.util.Optional<Person> findByEmail(String email) {
                return store.stream().filter(p -> p.email().equals(email)).findFirst();
            }
        };

        GetPersonsUseCase usecase = new GetPersonsUseCase(stubRepo);

        fr.univtln.bruno.demos.docker.application.pagination.PageResult<Person> page = usecase
                .execute(fr.univtln.bruno.demos.docker.application.pagination.PageRequest.ofPage(0, 10),
                        java.util.List.of());

        assertNotNull(page);
        assertEquals(2L, page.totalElements());
        assertEquals(1, page.totalPages());
        assertEquals(0, page.page());
        assertEquals(2, page.content().size());
        assertEquals("Alice", page.content().get(0).firstname());
        assertEquals("Bob", page.content().get(1).firstname());
    }
}
