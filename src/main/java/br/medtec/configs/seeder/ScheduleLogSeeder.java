package br.medtec.configs.seeder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.stream.IntStream;

@ApplicationScoped
class ScheduleLogSeeder implements Seeder {

    @Inject
    EntityManager em;

    @Override
    public int getOrder() {
        return 3;
    }

    @Transactional
    public void run() {
        IntStream.range(0, 10).forEach(i -> {
            String scheduleDate = "2025-06-23" + " 09:40:00";
            boolean taken = i % 2 == 0;
            String dateTaken = taken ? scheduleDate : null;
            int status = taken ? 1 : 0;

            em.createNativeQuery("INSERT INTO schedule_log (oid, oid_schedule, taken, date_taken, schedule_date, schedule_status, notification_sent, oid_user_creation, creation_date, oid_user_update, update_date, version) VALUES (:oid, :sched, :taken, :dateTaken, :schedDate, :status, :notified, :user, NOW(), NULL, NULL, 0)")
                    .setParameter("oid", "log-00" + i)
                    .setParameter("sched", "sched-00" + i)
                    .setParameter("taken", taken)
                    .setParameter("dateTaken", dateTaken)
                    .setParameter("schedDate", scheduleDate)
                    .setParameter("status", status)
                    .setParameter("notified", taken)
                    .setParameter("user", "person-00" + i)
                    .executeUpdate();
        });
    }
}
