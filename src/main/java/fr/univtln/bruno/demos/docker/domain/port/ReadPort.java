package fr.univtln.bruno.demos.docker.domain.port;

import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;
import fr.univtln.bruno.demos.docker.application.pagination.PageResult;
import fr.univtln.bruno.demos.docker.application.pagination.SortRequest;
import java.util.List;

import java.util.Optional;

/**
 * Read-only repository interface defining standard retrieval operations.
 * Aligns with Jakarta Data conventions for read operations.
 */
public interface ReadPort<T, ID> {
    Optional<T> findById(ID id);

    PageResult<T> findAll(PageRequest request, List<SortRequest> sortBy);

    long count();

    boolean existsById(ID id);
}
