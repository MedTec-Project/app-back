package br.medtec.features.schedule;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.image.ImageService;
import br.medtec.features.notification.MessageDTO;
import br.medtec.features.notification.NotificationWebSocket;
import br.medtec.features.schedule.schedulelog.ScheduleLogDTO;
import br.medtec.features.schedule.schedulelog.ScheduleLogService;
import br.medtec.utils.StringUtil;
import br.medtec.features.schedule.schedulelog.ScheduleLogRepository;
import br.medtec.utils.UtilDate;
import br.medtec.utils.Validations;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
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

        if (scheduleRepository.existsByAttribute("oidMedicine", scheduleDTO.getOidMedicine())) {
            throw new MEDBadRequestExecption("Já Existe Um Agendamento Para Este Medicamento");
        }

        Schedule schedule = scheduleDTO.toEntity();
        schedule = scheduleRepository.save(schedule);

        scheduleLogService.registerNextSchedule(schedule.getOid(), schedule.getInitialDate());

        return schedule;
    }

    @Transactional
    public ScheduleDTO updateSchedule(ScheduleDTO scheduleDTO, String oid) {
        validateSchedule(scheduleDTO);

        Schedule schedule = scheduleRepository.findByOid(oid);

        schedule.validateUser();

        Schedule updatedSchedule = scheduleDTO.toEntity(schedule);

        schedule = scheduleRepository.update(updatedSchedule);

        return schedule.toDTO();
    }

    @Transactional
    public void deleteSchedule(String oid) {
        Schedule schedule = scheduleRepository.findByOid(oid);

        schedule.validateUser();

        if (schedule.getScheduleLogs() != null && !schedule.getScheduleLogs().isEmpty()) {
            schedule.getScheduleLogs().forEach(scheduleLogRepository::delete);
        }

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
    public void markScheduleTaken(String oid, Boolean taken) {
        scheduleLogService.markScheduleTaken(oid, taken);
    }

    @Transactional
    public ScheduleDTO getSchedule(String oid) {
       Schedule schedule = scheduleRepository.findByOid(oid);
       return schedule.toDTO();
    }

    @Scheduled(every = "1m")
    public void checkSchedules() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWith10minPlus = now.plusMinutes(10);
        List<ScheduleLogDTO> getSchedulesNotfication = scheduleLogRepository.findSchedulesNotfication(now, nowWith10minPlus);

        getSchedulesNotfication.forEach(scheduleLogDTO -> {
            LocalDateTime timeLeft = LocalDateTime.now().minusMinutes(scheduleLogRepository.getIntervalByOidScheduleLog(scheduleLogDTO.getOidSchedule()));
            String message = "Em " + timeLeft.format(DateTimeFormatter.ofPattern("HH:mm")) + " tome o seu medicamento: " + scheduleLogDTO.getMedicineName();
            String date = UtilDate.formatDate(now);
            NotificationWebSocket.broadcast(new MessageDTO(message, date));
        });
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
