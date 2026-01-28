package fr.univtln.bruno.demos.docker.adapter.persistence;

import jakarta.data.Sort;
import jakarta.data.page.Page;
import jakarta.data.page.PageRequest;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Find;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

        @Find
        Optional<PersonEntity> findByEmail(String email);

        @Find
        Page<PersonEntity> findAll(jakarta.data.page.PageRequest pageRequest, Sort<? super PersonEntity>... sortBy);

        @Find
        Page<PersonEntity> findByFirstname(
                        String firstname,
                        PageRequest pageRequest,
                        Sort<? super PersonEntity>... sortBy);

        @Find
        Page<PersonEntity> findByLastname(
                        String lastname,
                        PageRequest pageRequest,
                        Sort<? super PersonEntity>... sortBy);

        @Query("SELECT COUNT(p) FROM PersonEntity p")
        long count();

        @Query("SELECT COUNT(p) > 0 FROM PersonEntity p WHERE p.id = :id")
        boolean existsById(Long id);

}
