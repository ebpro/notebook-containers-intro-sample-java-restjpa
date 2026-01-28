package fr.univtln.bruno.demos.docker.adapter.persistence;

import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;
import fr.univtln.bruno.demos.docker.application.pagination.PageResult;
import fr.univtln.bruno.demos.docker.application.pagination.SortRequest;
import fr.univtln.bruno.demos.docker.domain.model.Person;
import fr.univtln.bruno.demos.docker.domain.port.PersonReadPort;
import fr.univtln.bruno.demos.docker.domain.port.PersonWritePort;
import jakarta.data.Sort;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class PersonRepositoryAdapter implements PersonReadPort, PersonWritePort {

    private final PersonRepository dataRepo;
    private final PersonPersistenceMapper mapper;

    @Inject
    public PersonRepositoryAdapter(PersonRepository dataRepo, PersonPersistenceMapper mapper) {
        this.dataRepo = dataRepo;
        this.mapper = mapper;
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = mapper.toEntity(person);
        PersonEntity saved = dataRepo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return dataRepo.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public PageResult<Person> findAll(PageRequest request, List<SortRequest> sortBy) {
        jakarta.data.page.PageRequest jdRequest = PageRequestMapper.toJakarta(request);
        Sort<? super PersonEntity>[] sorts = SortMapper.toJakarta(sortBy);

        jakarta.data.page.Page<PersonEntity> entityPage = dataRepo.findAll(jdRequest, sorts);
        return PageMapper.map(entityPage, mapper::toDomain);
    }

    @Override
    public PageResult<Person> searchByFirstname(String firstname, PageRequest request, List<SortRequest> sortBy) {
        jakarta.data.page.PageRequest jdRequest = PageRequestMapper.toJakarta(request);
        Sort<? super PersonEntity>[] sorts = SortMapper.toJakarta(sortBy);

        return PageMapper.map(dataRepo.findByFirstname(firstname, jdRequest, sorts), mapper::toDomain);
    }

    @Override
    public PageResult<Person> searchByLastname(String lastname, PageRequest request, List<SortRequest> sortBy) {
        jakarta.data.page.PageRequest jdRequest = PageRequestMapper.toJakarta(request);
        Sort<? super PersonEntity>[] sorts = SortMapper.toJakarta(sortBy);

        return PageMapper.map(dataRepo.findByLastname(lastname, jdRequest, sorts), mapper::toDomain);
    }

    @Override
    public long count() {
        return dataRepo.count();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return dataRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return dataRepo.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        dataRepo.deleteById(id);
    }
}
