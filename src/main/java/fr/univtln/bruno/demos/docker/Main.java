package fr.univtln.bruno.demos.docker;

import fr.univtln.bruno.demos.docker.adapter.rest.GlobalExceptionMapper;
import fr.univtln.bruno.demos.docker.config.AppBinder;
import fr.univtln.bruno.demos.docker.config.AppConfig;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

/**
 * Main class to start the RESTful Java application using Jersey and Grizzly.
 * Configures the application components and starts the HTTP server.
 *
 * Usage: Run the main method to start the server at the specified BASE_URI.
 * The server hosts REST endpoints for managing persons and health checks.
 * Wiring is done manually in this class using AppConfig.
 * Shuts down gracefully on JVM exit, closing EntityManagerFactory and
 * DataSource.
 *
 * @See PersonResource
 * @See HealthResource
 * @See AppConfig
 */
@Slf4j
public class Main {

    public static final String BASE_URI = "http://0.0.0.0:8080/restjpa/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig()
                .packages("fr.univtln.bruno.demos.docker") // Scans EVERYTHING in this package
                .register(new AppBinder());

        // This tells Jersey to use the AppBinder to configure the internal HK2 locator
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        try {
            startServer();
            log.info("Server started at {}", BASE_URI);
            log.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            log.error("Server startup failed", e);
            System.exit(1);
        }
    }
}
