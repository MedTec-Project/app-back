package br.medtec.features.schedule.schedulelog;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class JpaScheduleLogRepository extends JpaGenericRepository<ScheduleLog> implements ScheduleLogRepository {
    public JpaScheduleLogRepository() {
        super(ScheduleLog.class);
    }

    @Override
    public List<ScheduleLogDTO> findToday() {
        QueryBuilder query = createConsultaNativa();

        query.transformDTO(ScheduleLogDTO.class)
                .select("s.oid, s.oid_schedule, s.schedule_status, s.schedule_date, m.image_path, m.name AS medicine_name, m.dosage, m.dosage_type, m.pharmaceutical_form, m.content, m.medicine_category")
                .from("""
            schedule_log s
            INNER JOIN (
                SELECT oid_schedule, MAX(schedule_date) AS max_date
                FROM schedule_log
                WHERE CAST(schedule_date AS DATE) = CURRENT_DATE
                GROUP BY oid_schedule
            ) grouped ON s.oid_schedule = grouped.oid_schedule AND s.schedule_date = grouped.max_date
         """)
                .from("LEFT JOIN medicine m ON m.oid = (SELECT oid_medicine FROM schedule WHERE schedule.oid = s.oid_schedule LIMIT 1)")
                .orderBy("s.schedule_date");

        return query.executeQuery();
    }

    @Override
    public List<ScheduleLogDTO> findGeneral() {
        QueryBuilder query = createConsultaNativa();

        query.transformDTO(ScheduleLogDTO.class)
                .select("s.oid, s.oid_schedule, s.schedule_status, s.schedule_date, m.image_path, m.name AS medicine_name, m.dosage, m.dosage_type, m.pharmaceutical_form, m.content, m.medicine_category")
                .from("""
            schedule_log s
            INNER JOIN (
                SELECT oid_schedule, MAX(schedule_date) AS max_date
                FROM schedule_log
                GROUP BY oid_schedule
            ) grouped ON s.oid_schedule = grouped.oid_schedule AND s.schedule_date = grouped.max_date
         """)
                .from("LEFT JOIN medicine m ON m.oid = (SELECT oid_medicine FROM schedule WHERE schedule.oid = s.oid_schedule LIMIT 1)")
                .orderBy("s.schedule_date")
                .limit(40);

        return query.executeQuery();
    }
}
