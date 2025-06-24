package br.medtec.features.appointment;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.notification.MessageDTO;
import br.medtec.features.notification.NotificationWebSocket;
import br.medtec.utils.*;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Inject
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public Appointment createAppointment(AppointmentDTO appointmentDTO) {
        validateAppointment(appointmentDTO);
        Appointment appointment = appointmentDTO.toEntity();

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public Appointment updateAppointment(AppointmentDTO appointmentDTO, String oid) {
        validateAppointment(appointmentDTO);
        Appointment appointment = appointmentRepository.findByOid(oid);
        appointment.validateUser();
        Appointment updatedAppointment = appointmentDTO.toEntity(appointment);
        return appointmentRepository.update(updatedAppointment);
    }

    @Transactional
    public void deleteAppointment(String oid) {
        Appointment appointment = appointmentRepository.findByOid(oid);
        appointment.validateUser();
        appointmentRepository.delete(appointment);
    }

    @Transactional
    public AppointmentDTO findAppointment(String oid) {
        Appointment appointment = appointmentRepository.findByOid(oid);
        appointment.validateUser();
        return appointment.toDTO();
    }

    @Scheduled(every = "10s")
    public void getNotificationsAppointment() {
        List<AppointmentDTO> appointments = appointmentRepository.findAppointmentsNotifications();
        sendNotification(appointments);
    }

    public void sendNotification(List<AppointmentDTO> appointments) {
        List<MessageDTO> messages = buildMessages(appointments);
        if (UtilCollection.isCollectionValid(messages)) {
            NotificationWebSocket.sendMessages(messages);
        }
    }

    public List<MessageDTO> buildMessages(List<AppointmentDTO> appointments) {
        List<MessageDTO> messages = new ArrayList<>();
        if (UtilCollection.isCollectionValid(appointments)) {
            appointments.forEach(appointmentDTO -> {
                Date scheduleDate = UtilDate.getDateTimeByFormatedString(appointmentDTO.getScheduleDate());
                String timeLeft = UtilDate.timeLeftInMinutes(scheduleDate);
                String message = "Você tem uma consulta agendada em " + timeLeft + " minutos com o doutor(a) " + appointmentDTO.getNameDoctor();
                messages.add(new MessageDTO(appointmentDTO.getOid(), message, UtilDate.formatTimestamp(LocalDateTime.now())));
            });
        }
        return messages;
    }

    @Transactional
    public void markAppointmentDone(String oid, AppointmentDoneDTO appointmentDoneDTO) {
        Appointment appointment = appointmentRepository.findByOid(oid);
        appointment.validateUser();
        appointment.setDone(!appointmentDoneDTO.isDone());
        appointmentRepository.update(appointment);
    }

    @Transactional
    public void validateAppointment(AppointmentDTO appointmentDTO) {
        Validations validations = new Validations(this);
        if (appointmentDTO == null) {
            throw new MEDBadRequestExecption("Médico Não Informado");
        }

        if (!StringUtil.isValidString(appointmentDTO.getOidDoctor())) {
            validations.add("Médico não pode ser nulo");
        }

        if (!StringUtil.isValidString(appointmentDTO.getScheduleDate())) {
            validations.add("Data/Hora nao pode ser nula");
        }

        validations.throwErrors();
    }
}
