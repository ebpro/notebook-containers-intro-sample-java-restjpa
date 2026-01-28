package fr.univtln.bruno.demos.docker.config;

import fr.univtln.bruno.demos.docker.adapter.persistence.PersonRepositoryAdapter;
import fr.univtln.bruno.demos.docker.adapter.persistence.DataSourceHealthService;
import fr.univtln.bruno.demos.docker.domain.port.PersonReadPort;
import fr.univtln.bruno.demos.docker.domain.port.PersonWritePort;
import fr.univtln.bruno.demos.docker.domain.service.HealthService;
import jakarta.inject.Singleton;
import jakarta.inject.Named;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import javax.sql.DataSource;
import java.util.Set;

public class AppBinder extends AbstractBinder {

        private static final String BASE_PACKAGE = "fr.univtln.bruno.demos.docker";

        @Override
        protected void configure() {

                // ------------------------------------------------------------------
                // 1️⃣ Infrastructure (EXPLICIT & BORING – good!)
                // ------------------------------------------------------------------

                bind(AppConfig.dataSource())
                                .to(DataSource.class);

                bind(PersonRepositoryAdapter.class)
                                .to(PersonReadPort.class)
                                .to(PersonWritePort.class)
                                .in(Singleton.class);

                bind(DataSourceHealthService.class)
                                .to(HealthService.class)
                                .in(Singleton.class);

                // ------------------------------------------------------------------
                // 2️⃣ Application / Use Cases (AUTO-DISCOVERY)
                // ------------------------------------------------------------------

                Reflections reflections = new Reflections(
                                BASE_PACKAGE,
                                Scanners.TypesAnnotated);

                Set<Class<?>> components = reflections.getTypesAnnotatedWith(Singleton.class);

                components.addAll(
                                reflections.getTypesAnnotatedWith(Named.class));

                for (Class<?> clazz : components) {
                        if (clazz.isInterface())
                                continue;

                        // Bind class to itself (respect annotation scope)
                        bindAsContract(clazz);

                        // Bind ONLY to domain ports
                        for (Class<?> iface : clazz.getInterfaces()) {
                                if (isDomainPort(iface)) {
                                        bind(clazz).to(iface);
                                }
                        }
                }
        }

        private boolean isDomainPort(Class<?> iface) {
                return iface.getPackageName()
                                .startsWith("fr.univtln.bruno.demos.docker.domain.port");
        }
}
