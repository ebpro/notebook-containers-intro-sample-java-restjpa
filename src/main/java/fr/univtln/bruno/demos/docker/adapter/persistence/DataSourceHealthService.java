package fr.univtln.bruno.demos.docker.adapter.persistence;

import fr.univtln.bruno.demos.docker.domain.service.HealthService;
import fr.univtln.bruno.demos.docker.config.AppConfig;

import javax.sql.DataSource;
import java.sql.Connection;

public class DataSourceHealthService implements HealthService {

    @Override
    public boolean dbIsUp() {
        DataSource ds = AppConfig.dataSource();
        try (Connection c = ds.getConnection()) {
            // simple validation query
            try (var ps = c.prepareStatement("SELECT 1")) {
                return ps.execute();
            }
        } catch (Exception e) {
            return false;
        }
    }
}
