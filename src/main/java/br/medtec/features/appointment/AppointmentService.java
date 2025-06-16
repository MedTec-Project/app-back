package br.medtec.features.appointment;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.StringUtil;
import br.medtec.utils.UserSession;
import br.medtec.utils.Validations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

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
    public Appointment findAppointment(String oid) {
        Appointment appointment = appointmentRepository.findByOid(oid);
        if (UserSession.getOidUser().equals(appointment.getOidUserCreation())) {
            return appointment;
        } else {
            log.warn("Você {} não tem permissão para acessar este médico {}", UserSession.getOidUser(), oid);
            throw new MEDBadRequestExecption("Você não tem permissão para acessar este recurso");
        }
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

        if (!StringUtil.isValidString(appointmentDTO.getDate())) {
            validations.add("Data/Hora nao pode ser nula");
        }

        validations.throwErrors();
    }
}
