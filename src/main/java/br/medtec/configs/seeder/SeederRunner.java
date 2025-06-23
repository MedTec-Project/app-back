package br.medtec.configs.seeder;

import io.quarkus.runtime.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@ApplicationScoped
@Slf4j
public class SeederRunner {

    @Inject
    Instance<Seeder> seeders;

    void onStart(@Observes StartupEvent ev) {
        log.info("Iniciando SeederRunner...");
        seeders.stream()
                .sorted(Comparator.comparingInt(Seeder::getOrder))
                .forEach(Seeder::run);
    }
}
