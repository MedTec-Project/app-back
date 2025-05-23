package br.medtec.features.doctor;

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
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Inject
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor createDoctor(DoctorDTO doctorDTO) {
        validateDoctor(doctorDTO);
        Doctor doctor = doctorDTO.toEntity();

        if (doctorRepository.existsByCrm(doctor.getCrm())) {
            log.warn("CRM já cadastrado {}", doctor.getCrm());
            throw new MEDBadRequestExecption("CRM já cadastrado");
        }

        return doctorRepository.save(doctor);
    }

    @Transactional
    public Doctor updateDoctor(DoctorDTO doctorDTO, String oid) {
        validateDoctor(doctorDTO);
        Doctor doctor = doctorRepository.findByOid(oid);
        doctor.validateUser();
        Doctor updatedDoctor = doctorDTO.toEntity(doctor);
        return doctorRepository.update(updatedDoctor);
    }

    @Transactional
    public void deleteDoctor(String oid) {
        Doctor doctor = doctorRepository.findByOid(oid);
        doctor.validateUser();
        doctorRepository.delete(doctor);
    }

    @Transactional
    public Doctor findDoctor(String oid) {
        Doctor doctor = doctorRepository.findByOid(oid);
        if (UserSession.getOidUser().equals(doctor.getOidUserCreation())) {
            return doctor;
        } else {
            log.warn("Você {} não tem permissão para acessar este médico {}", UserSession.getOidUser(), oid);
            throw new MEDBadRequestExecption("Você não tem permissão para acessar este recurso");
        }
    }

    @Transactional
    public void validateDoctor(DoctorDTO doctorDTO) {
        Validations validations = new Validations(this);
        if (doctorDTO == null) {
            throw new MEDBadRequestExecption("Médico Não Informado");
        }

        if (!StringUtil.isValidString(doctorDTO.getName())) {
            validations.add("Nome não pode ser nulo");
        }

        if (!StringUtil.isValidString(doctorDTO.getCrm())) {
            validations.add("CRM não pode ser nulo");
        }

        if (!StringUtil.isValidPhoneOrNull(doctorDTO.getPhone())) {
            validations.add("Telefone inválido");
        }

        if (!StringUtil.isValidEmailOrNull(doctorDTO.getContactEmail())) {
            validations.add("Email inválido");
        }

        validations.throwErrors();
    }
}
