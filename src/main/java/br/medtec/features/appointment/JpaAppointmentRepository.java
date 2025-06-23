package br.medtec.features.appointment;

import br.medtec.generics.JpaGenericRepository;
import br.medtec.utils.QueryBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class JpaAppointmentRepository extends JpaGenericRepository<Appointment> implements AppointmentRepository {


    public JpaAppointmentRepository() {
        super(Appointment.class);
    }

    @Override
    public List<AppointmentDTO> findAppointmentsToday() {
        QueryBuilder queryBuilder = createConsultaNativa();
        queryBuilder.transformDTO(AppointmentDTO.class);
        queryBuilder.select("a.oid, a.schedule_date, a.reminder, a.oid_doctor, p.name AS doctorName")
                .from("Appointment a")
                .from("LEFT JOIN person p ON p.oid = a.oid_doctor");

        queryBuilder.where("a.done IS DISTINCT FROM TRUE AND a.schedule_date >= :today AND a.schedule_date < :tomorrow");
        queryBuilder.param("today", LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        queryBuilder.param("tomorrow", LocalDateTime.now().withHour(0) .withMinute(0).withSecond(0).plusDays(1));

        return queryBuilder.executeQuery();
    }

    @Override
    public List<AppointmentDTO> findAppointmentsNotifications() {
        QueryBuilder queryBuilder = createConsultaNativa();
        queryBuilder.transformDTO(AppointmentDTO.class)
                .select("a.oid, a.schedule_date, a.reminder, a.oid_doctor, p.name AS doctorName")
                .from("appointment a")
                .from("LEFT JOIN person p ON p.oid = a.oid_doctor");
        queryBuilder.where("a.done IS DISTINCT FROM TRUE AND a.notification_sent IS DISTINCT FROM TRUE AND a.schedule_date >= :now AND a.schedule_date <= :nowWith30minPlus");
        queryBuilder.param("now", LocalDateTime.now());
        queryBuilder.param("nowWith30minPlus", LocalDateTime.now().plusMinutes(30));

        return queryBuilder.executeQuery();
    }

    @Override
    public List findAll() {
        QueryBuilder queryBuilder = createConsultaNativa();
        queryBuilder.transformDTO(AppointmentDTO.class)
                .select("a.oid, a.schedule_date, a.reminder, a.oid_doctor, p.name AS doctorName")
                .from("appointment a")
                .from("LEFT JOIN person p ON p.oid = a.oid_doctor");

        queryBuilder.where("a.done IS DISTINCT FROM TRUE");

        return queryBuilder.executeQuery();
    }
}
