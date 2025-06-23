package br.medtec.configs;

import br.medtec.configs.seeder.SeederRunner;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class StartupTrigger {

    @Inject
    SeederRunner seederRunner;

    @PostConstruct
    public void trigger() {
        log.info("Disparando SeederRunner...");
        // Apenas acessar o seederRunner já força sua criação e executa o @PostConstruct dele.
    }

}
