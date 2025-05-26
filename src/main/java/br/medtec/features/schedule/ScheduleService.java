package br.medtec.features.schedule;

import br.medtec.features.image.ImageService;
import br.medtec.features.schedule.schedulelog.ScheduleLogDTO;
import br.medtec.features.schedule.schedulelog.ScheduleLogService;
import br.medtec.utils.StringUtil;
import br.medtec.features.schedule.schedulelog.ScheduleLogRepository;
import br.medtec.utils.Validations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleLogRepository scheduleLogRepository;

    private final ImageService imageService;

    @Inject
    ScheduleLogService scheduleLogService;

    @Inject
    public ScheduleService(ScheduleRepository scheduleRepository,
                           ScheduleLogRepository scheduleLogRepository,
                           ImageService imageService) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleLogRepository = scheduleLogRepository;
        this.imageService = imageService;
    }

    @Transactional
    public Schedule registerSchedule(ScheduleDTO scheduleDTO) {
        validateSchedule(scheduleDTO);

        Schedule schedule = scheduleDTO.toEntity();
        schedule = scheduleRepository.save(schedule);

        scheduleLogService.registerNextSchedule(schedule.getOid(), schedule.getInitialDate(), schedule.getInterval());

        return schedule;
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
    public List<ScheduleLogDTO> getSchedulesToday() {
        List<ScheduleLogDTO> schedulesToday = scheduleLogRepository.findToday();
        schedulesToday.forEach(scheduleLogDTO -> scheduleLogDTO.setImageBase64(imageService.getImage(scheduleLogDTO.getImagePath())));
        return schedulesToday;
    }

    @Transactional
    public List<ScheduleLogDTO> getSchedulesGeneral() {
        List<ScheduleLogDTO> schedulesGeneral = scheduleLogRepository.findGeneral();
        schedulesGeneral.forEach(scheduleLogDTO -> scheduleLogDTO.setImageBase64(imageService.getImage(scheduleLogDTO.getImagePath())));
        return schedulesGeneral;
    }

    @Transactional
    public void markScheduleTaken(String oid, Boolean dateTaken) {
        scheduleLogService.markScheduleTaken(oid, dateTaken);
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

        if (scheduleRepository.existsByAttribute("oidMedicine", scheduleDTO.getOidMedicine())) {
            validations.add("Já Existe Um Agendamento Para Este Medicamento");
        }

        validations.throwErrors();
    }
}
