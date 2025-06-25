package br.medtec.features.schedule;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.history.HistoryService;
import br.medtec.features.history.HistoryType;
import br.medtec.features.image.ImageService;
import br.medtec.features.notification.MessageDTO;
import br.medtec.features.notification.NotificationWebSocket;
import br.medtec.features.schedule.schedulelog.ScheduleLog;
import br.medtec.features.schedule.schedulelog.ScheduleLogDTO;
import br.medtec.features.schedule.schedulelog.ScheduleLogService;
import br.medtec.utils.StringUtil;
import br.medtec.features.schedule.schedulelog.ScheduleLogRepository;
import br.medtec.utils.UtilCollection;
import br.medtec.utils.UtilDate;
import br.medtec.utils.Validations;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final ScheduleLogRepository scheduleLogRepository;

    private final ImageService imageService;

    @Inject
    HistoryService historyService;

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

        historyService.save("Criado Novo Agendamento", HistoryType.INSERT);
        return schedule;
    }

    @Transactional
    public ScheduleDTO updateSchedule(ScheduleDTO scheduleDTO, String oid) {
        validateSchedule(scheduleDTO);

        Schedule schedule = scheduleRepository.findByOid(oid);

        schedule.validateUser();

        Schedule updatedSchedule = scheduleDTO.toEntity(schedule);

        schedule = scheduleRepository.update(updatedSchedule);

        historyService.save("Atualizado Agendamento", HistoryType.UPDATE);

        return schedule.toDTO();
    }

    @Transactional
    public void deleteSchedule(String oid) {
        Schedule schedule = scheduleRepository.findByOid(oid);

        schedule.validateUser();

        if (schedule.getScheduleLogs() != null && !schedule.getScheduleLogs().isEmpty()) {
            schedule.getScheduleLogs().forEach(scheduleLogRepository::delete);
        }

        historyService.save("Deletado Agendamento", HistoryType.DELETE);

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

    @Scheduled(every = "10s")
    public void checkSchedules() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowWith10minPlus = now.plusMinutes(10);
        List<ScheduleLogDTO> schedulesToNotify = scheduleLogRepository.findSchedulesNotfication(now, nowWith10minPlus);
        sendNotification(schedulesToNotify);
    }

    @Transactional
    public void sendNotification(List<ScheduleLogDTO> scheduleLogs) {
        List<MessageDTO> messages = buildMessages(scheduleLogs);
        if (UtilCollection.isCollectionValid(messages)) {
            NotificationWebSocket.sendMessages(messages);
        }
    }

    @Transactional
    public List<MessageDTO> buildMessages(List<ScheduleLogDTO> scheduleLogs) {
        List<MessageDTO> messages = new ArrayList<>();
        if (UtilCollection.isCollectionValid(scheduleLogs)) {
            scheduleLogs.forEach(scheduleLogDTO -> {
                Date scheduleDate = scheduleLogDTO.getScheduleDate();
                String timeLeft = UtilDate.timeLeftInMinutes(scheduleDate);
                String message = "Em " + timeLeft + " minutos tome o seu medicamento: " + scheduleLogDTO.getMedicineName();
                scheduleLogService.setNotificationSent(scheduleLogDTO.getOid());
                messages.add(new MessageDTO(scheduleLogDTO.getOid(), message, UtilDate.formatTimestamp(LocalDateTime.now())));
            });
        }
        return messages;
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
