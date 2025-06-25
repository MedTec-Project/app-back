package br.medtec.features.appointment;

import br.medtec.generics.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

public interface AppointmentRepository extends GenericRepository<Appointment> {

    List<AppointmentDTO> findAppointmentsToday();
    List<AppointmentDTO> findAppointmentsNotifications();
    List<AppointmentDTO> findAllEvents();
}
