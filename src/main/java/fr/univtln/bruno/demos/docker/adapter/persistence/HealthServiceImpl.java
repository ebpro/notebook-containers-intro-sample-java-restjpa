package fr.univtln.bruno.demos.docker.adapter.persistence;

import fr.univtln.bruno.demos.docker.domain.service.HealthService;

import fr.univtln.bruno.demos.docker.application.pagination.PageRequest;

public class HealthServiceImpl implements HealthService {

    private final PersonRepository repo;

    public HealthServiceImpl(PersonRepository repo) {
        this.repo = repo;
    }

    @Override
    public boolean dbIsUp() {
        try {
            repo.count();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
