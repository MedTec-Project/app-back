package br.medtec.features.calendar;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.appointment.Appointment;
import br.medtec.features.appointment.AppointmentDTO;
import br.medtec.features.appointment.AppointmentRepository;
import br.medtec.features.schedule.schedulelog.ScheduleLogDTO;
import br.medtec.features.schedule.schedulelog.ScheduleLogRepository;
import br.medtec.utils.StringUtil;
import br.medtec.utils.UtilDate;
import br.medtec.utils.Validations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CalendarService {

    final AppointmentRepository appointmentRepository;

    final ScheduleLogRepository scheduleLogRepository;

    final EventRepository eventRepository;

    @Inject
    public CalendarService(AppointmentRepository appointmentRepository, ScheduleLogRepository scheduleLogRepository, EventRepository eventRepository) {
        this.appointmentRepository = appointmentRepository;
        this.scheduleLogRepository = scheduleLogRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public List<EventDTO> getEvents() {
        List<EventDTO> events = new ArrayList<>();
        List<ScheduleLogDTO> scheduleLogs = scheduleLogRepository.findAllEvents();
        List<AppointmentDTO> appointments = appointmentRepository.findAllEvents();
        List<RegisterEventDTO> eventsRegistered = eventRepository.findAllEvents();

        for (ScheduleLogDTO scheduleLog : scheduleLogs) {
            events.add(transformScheduleLog(scheduleLog));
        }

        for (AppointmentDTO appointment : appointments) {
            events.add(transformAppointment(appointment));
        }

        for (RegisterEventDTO event : eventsRegistered) {
            events.add(event.toEvent());
        }

        return events;
    }

    @Transactional
    public EventDTO transformScheduleLog(ScheduleLogDTO scheduleLog) {
        String description = "Lembrete: " + scheduleLog.getReminder();
        return new EventDTO(scheduleLog.getOid(), EventType.SCHEDULE_LOG, scheduleLog.getMedicineName(), scheduleLog.getScheduleDate(), description);
    }

    @Transactional
    public EventDTO transformAppointment(AppointmentDTO appointment) {
        String title = "Consulta com " + appointment.getNameDoctor();
        String description = "Lembrete: " + appointment.getReminder();
        return new EventDTO(appointment.getOid(), EventType.APPOINTMENT, title, UtilDate.getDateTimeByFormatedString(appointment.getScheduleDate()), description);
    }

    @Transactional
    public Event updateEvent(RegisterEventDTO eventDTO, String oid) {
        validateEvent(eventDTO);
        Event event = eventRepository.findByOid(oid);
        event.validateUser();
        Event updatedEvent = eventDTO.toEntity(event);
        return eventRepository.update(updatedEvent);
    }

    @Transactional
    public void deleteEvent(String oid) {
        Event event = eventRepository.findByOid(oid);
        event.validateUser();
        eventRepository.delete(event);
    }

    @Transactional
    public RegisterEventDTO findEvent(String oid) {
        Event event = eventRepository.findByOid(oid);
        event.validateUser();
        return event.toDTO();
    }

    @Transactional
    public Event createEvent(RegisterEventDTO eventDTO) {
        validateEvent(eventDTO);
        Event event = eventDTO.toEntity();
        return eventRepository.save(event);
    }

    @Transactional
    public void validateEvent(RegisterEventDTO eventDTO) {
        Validations validations = new Validations(this);
        if (eventDTO == null) {
            throw new MEDBadRequestExecption("Evento nao pode ser nulo");
        }

        if (!StringUtil.isValidString(eventDTO.getTitle())) {
            validations.add("Titulo nao pode ser nulo");
        }

        if (!StringUtil.isValidString(eventDTO.getScheduleDate())) {
            validations.add("Data nao pode ser nula");
        }

        validations.throwErrors();


    }
}
