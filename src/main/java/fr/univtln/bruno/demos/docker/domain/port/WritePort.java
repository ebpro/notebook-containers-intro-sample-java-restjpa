package fr.univtln.bruno.demos.docker.domain.port;

/**
 * Write repository interface defining standard write operations.
 * Aligns with Jakarta Data conventions for write operations.
 */
public interface WritePort<T, ID> {
    T save(T entity);

    void deleteById(ID id);
}
