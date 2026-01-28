package fr.univtln.bruno.demos.docker;

import org.glassfish.jersey.server.ResourceConfig;
import fr.univtln.bruno.demos.docker.config.AppBinder;

public class JerseyApp extends ResourceConfig {
    public JerseyApp() {
        packages("fr.univtln.bruno.demos.docker.adapter.rest");
        register(new AppBinder());
    }
}
