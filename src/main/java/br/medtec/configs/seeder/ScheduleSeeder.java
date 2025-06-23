package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.stream.IntStream;

@ApplicationScoped
class ScheduleSeeder implements Seeder {

    @Inject
    EntityManager em;

    @Override
    public int getOrder() { return 2; }

    @Transactional
    public void run() {
        IntStream.range(0, 10).forEach(i ->
                em.createNativeQuery("INSERT INTO schedule (oid, oid_user_creation, creation_date, oid_user_update, update_date, version, oid_medicine, oid_doctor, initial_date, final_date, quantity, interval_medicine, reminder) VALUES (:oid, :creator, NOW(), NULL, NULL, 0, :med, :doc, '2025-06-01', '2025-06-10', 1, 1, '08:00')")
                        .setParameter("oid", "sched-00" + i)
                        .setParameter("creator", "person-00" + i)
                        .setParameter("med", "med-00" + i)
                        .setParameter("doc", "person-001")
                        .executeUpdate()
        );
    }
}
