package br.medtec.features.schedule.schedulelog;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
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
                .select("s.oid, s.oid_schedule, s.taken, s.schedule_status, s.schedule_date, m.image_path, m.name AS medicine_name, m.dosage, m.dosage_type, m.pharmaceutical_form, m.content, m.medicine_category")
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

        query.limit(40);

        return query.executeQuery();
    }

    @Override
    public List<ScheduleLogDTO> findGeneral() {
        QueryBuilder query = createConsultaNativa();

        query.transformDTO(ScheduleLogDTO.class)
                .select("""
                        t.oid, t.oid_schedule, t.taken, t.schedule_status, t.schedule_date, 
                        t.image_path, t.name AS medicine_name, t.dosage, t.dosage_type, 
                        t.pharmaceutical_form, t.content, t.medicine_category
                    """)
                .from("""
                        (
                            SELECT
                                s.*,
                                m.image_path, m.name, m.dosage, m.dosage_type, 
                                m.pharmaceutical_form, m.content, m.medicine_category,
                                ROW_NUMBER() OVER (
                                    PARTITION BY s.oid_schedule
                                    ORDER BY s.schedule_date ASC
                                ) AS rn
                            FROM schedule_log s
                            INNER JOIN schedule sched ON sched.oid = s.oid_schedule
                            INNER JOIN medicine m ON m.oid = sched.oid_medicine
                            WHERE s.taken IS DISTINCT FROM TRUE
                        ) t
                    """)
                .where("t.rn = 1")
                .orderBy("t.schedule_date")
                .limit(40);

        return query.executeQuery();
    }


    @Override
    public Integer getIntervalByOidScheduleLog(String oidScheduleLog) {
        QueryBuilder query = createQueryBuilder();

        query.select("s.intervalMedicine")
                .from("ScheduleLog sl")
                .from("JOIN sl.schedule s")
                .where("sl.oidSchedule = :oid")
                .param("oid", oidScheduleLog);
        return (Integer) query.firstResult();
    }

    @Override
    public ScheduleLog findNextSchedule(String oidScheduleLog) {
        QueryBuilder query = createConsultaNativa();

        query.select("sl.*")
                .from("schedule_log sl")
                .where("sl.oid_schedule = :oid AND sl.taken IS DISTINCT FROM TRUE")
                .param("oid", oidScheduleLog);

        return (ScheduleLog) query.firstResult();
    }

    @Override
    public List<ScheduleLogDTO> findSchedulesNotfication(LocalDateTime now, LocalDateTime nowWith10minPlus) {
        QueryBuilder query = createConsultaNativa();

        query.transformDTO(ScheduleLogDTO.class)
                .select("sl.oid, sl.oid_schedule, m.name, sl.schedule_date")
                .from("schedule_log sl")
                .from("INNER JOIN medicine m ON m.oid = (SELECT oid_medicine FROM schedule WHERE schedule.oid = sl.oid_schedule LIMIT 1)")
                .where("sl.taken IS DISTINCT FROM TRUE AND sl.notification_sent IS DISTINCT FROM TRUE AND sl.schedule_date >= :now AND sl.schedule_date <= :nowWith10minPlus")
                .param("now", now)
                .param("nowWith10minPlus", nowWith10minPlus);

        return query.executeQuery();
    }

}
