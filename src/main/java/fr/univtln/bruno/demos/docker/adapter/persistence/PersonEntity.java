/**
 * Entity class representing a person in the persistence layer.
 * <p>
 * This class is mapped to the "persons" table in the database.
 * Each person has a unique identifier, email, first name, and last name.
 * </p>
 *
 * <p>
 * Fields:
 * <ul>
 *   <li>id - Primary key, auto-generated.</li>
 *   <li>email - Unique and non-null email address of the person.</li>
 *   <li>firstname - First name of the person.</li>
 *   <li>lastname - Last name of the person.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Annotations:
 * <ul>
 *   <li>{@code @Entity} - Marks this class as a JPA entity.</li>
 *   <li>{@code @Table(name = "persons")} - Maps the entity to the "persons" table.</li>
 *   <li>{@code @Id}, {@code @GeneratedValue} - Specifies the primary key and its generation strategy.</li>
 *   <li>{@code @Column(nullable = false, unique = true)} - Enforces constraints on the email field.</li>
 * </ul>
 * </p>
 */
package fr.univtln.bruno.demos.docker.adapter.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "persons")

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String firstname;
    private String lastname;

    public PersonEntity(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
