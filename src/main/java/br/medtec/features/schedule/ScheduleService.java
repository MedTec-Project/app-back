package br.medtec.features.schedule;

import br.medtec.features.image.ImageService;
import br.medtec.features.schedule.Schedule;
import br.medtec.features.schedule.ScheduleDTO;
import br.medtec.features.schedule.ScheduleRepository;
import br.medtec.utils.StringUtil;
import br.medtec.utils.Validations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Inject
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public Schedule registerSchedule(ScheduleDTO scheduleDTO) {
        validateSchedule(scheduleDTO);

        Schedule schedule = scheduleDTO.toEntity();

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule updateSchedule(ScheduleDTO scheduleDTO, String oid) {
        validateSchedule(scheduleDTO);

        Schedule schedule = scheduleRepository.findByOid(oid);

        schedule.validateUser();

        Schedule updatedSchedule = scheduleDTO.toEntity(schedule);

        return scheduleRepository.update(updatedSchedule);
    }

    @Transactional
    public void deleteSchedule(String oid) {
        Schedule schedule = scheduleRepository.findByOid(oid);
        schedule.validateUser();
        scheduleRepository.delete(schedule);
    }

    @Transactional
    public void validateSchedule(ScheduleDTO scheduleDTO) {
        Validations validations = new Validations();

        if (!StringUtil.isValidString(scheduleDTO.getOidMedicine())) {
            validations.add("O Medicamento Não Pode Ser Nulo");
        }

        if (scheduleDTO.getQuantity() == null || scheduleDTO.getQuantity() <= 0) {
            validations.add("Quantidade Não Pode Ser Nula");
        }

        if (scheduleDTO.getInitialDate() == null) {
            validations.add("Data Inicial Não Pode Ser Nula");
        }

        validations.throwErrors();
    }
}
