package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@ApplicationScoped
public class SymptomSeeder implements Seeder {

    @Inject
    EntityManager em;

    @Transactional
    @Override
    public void run() {
        List<String> symptoms = List.of(
                "Diarreia", "Hipertrofia", "Hemorragia", "Infecção", "Lesão", "Necrose", "Obesidade",
                "Osteoporose", "Pânico", "Renal", "Síndrome de Down", "Síndrome de Parkinson",
                "Síndrome de Tourette", "Síndrome do Parkinson", "Síndrome do Tourette",
                "Traumatismo", "Tumor", "Vômito", "Dor de cabeça", "Dor de garganta", "Febre", "Gripe");

        symptoms.forEach(name ->
                em.createNativeQuery("INSERT INTO symptom (name, creation_date, oid_user_creation, oid) VALUES (:name, CURRENT_TIMESTAMP, 'user', :oid)")
                        .setParameter("name", name)
                        .setParameter("oid", UUID.randomUUID().toString())
                        .executeUpdate()
        );

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
