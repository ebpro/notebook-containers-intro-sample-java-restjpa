package fr.univtln.bruno.demos.docker.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.univtln.bruno.demos.docker.adapter.persistence.PersonRepository;
import fr.univtln.bruno.demos.docker.adapter.persistence.PersonRepositoryAdapter;
import fr.univtln.bruno.demos.docker.adapter.persistence.PersonPersistenceMapper;
import fr.univtln.bruno.demos.docker.application.usecase.CreatePersonUseCase;
import fr.univtln.bruno.demos.docker.application.usecase.GetPersonsUseCase;
import fr.univtln.bruno.demos.docker.domain.port.PersonReadPort;
import fr.univtln.bruno.demos.docker.domain.port.PersonWritePort;
import org.mapstruct.factory.Mappers;

import javax.sql.DataSource;

public final class AppConfig {

    private AppConfig() {
    }

    private static HikariDataSource ds;

    public static synchronized DataSource dataSource() {
        if (ds != null) {
            return ds;
        }

        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new IllegalStateException("DB_URL, DB_USER and DB_PASSWORD are required environment variables.");
        }

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(dbUrl);
        cfg.setUsername(dbUser);
        cfg.setPassword(dbPassword);

        String maxPool = System.getenv("DB_MAX_POOL");
        if (maxPool != null) {
            try {
                cfg.setMaximumPoolSize(Integer.parseInt(maxPool));
            } catch (NumberFormatException ignored) {
            }
        }

        String minIdle = System.getenv("DB_MIN_IDLE");
        if (minIdle != null) {
            try {
                cfg.setMinimumIdle(Integer.parseInt(minIdle));
            } catch (NumberFormatException ignored) {
            }
        }

        if (cfg.getMaximumPoolSize() == 0) {
            cfg.setMaximumPoolSize(10);
        }

        ds = new HikariDataSource(cfg);
        return ds;
    }

    public static void closeDataSource() {
        if (ds != null) {
            try {
                ds.close();
            } catch (Exception ignored) {
            }
            ds = null;
        }
    }

    /**
     * Configure the PersonRepository using Jakarta Data.
     */
    public static PersonReadPort personReadPort(PersonRepository dataRepo) {
        PersonPersistenceMapper mapper = Mappers.getMapper(PersonPersistenceMapper.class);
        return new PersonRepositoryAdapter(dataRepo, mapper);
    }

    public static PersonWritePort personWritePort(PersonRepository dataRepo) {
        PersonPersistenceMapper mapper = Mappers.getMapper(PersonPersistenceMapper.class);
        return new PersonRepositoryAdapter(dataRepo, mapper);
    }

    public static CreatePersonUseCase getCreatePersonUseCase(PersonWritePort repo) {
        return new CreatePersonUseCase(repo, fr.univtln.bruno.demos.docker.application.mapper.PersonDtoMapper.INSTANCE);
    }
}
