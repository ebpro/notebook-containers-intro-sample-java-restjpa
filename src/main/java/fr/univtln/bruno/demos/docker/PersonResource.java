package fr.univtln.bruno.demos.docker;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("persons")
public class PersonResource {
    @GET
    public List<Person> findAll() {
        try (EntityManager em = Main.emf.createEntityManager()) {
            return em.createNamedQuery("person.findall", Person.class).getResultList();
        }
    }
}
