package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;
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
                "Traumatismo", "Tumor", "Vômito"
        );

        symptoms.forEach(name ->
                em.createNativeQuery("INSERT INTO symptom (name, creation_date, oid_user_creation, oid) VALUES (:name, CURRENT_TIMESTAMP, 'user', RANDOM_UUID())")
                        .setParameter("name", name)
                        .executeUpdate()
        );

        IntStream.range(0, 10).forEach(i ->
                em.createNativeQuery("INSERT INTO symptom (oid, name, oid_user_creation, creation_date, oid_user_update, update_date, version) VALUES (:oid, :name, 'admin-user', NOW(), NULL, NULL, 0)")
                        .setParameter("oid", "symp-00" + i)
                        .setParameter("name", "Sintoma" + i)
                        .executeUpdate()
        );
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
