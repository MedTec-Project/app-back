package br.medtec.features.schedule.schedulelog;

import br.medtec.features.schedule.Schedule;
import br.medtec.features.schedule.ScheduleStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.BooleanUtils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ScheduleLogService {

    private final ScheduleLogRepository scheduleLogRepository;

    @Inject
    public ScheduleLogService(ScheduleLogRepository scheduleLogRepository) {
        this.scheduleLogRepository = scheduleLogRepository;
    }

    @Transactional
    public void registerNextSchedule(String oidSchedule, Date datePara) {
        registerNextSchedule(oidSchedule, datePara, null);
    }

    @Transactional
    public void registerNextSchedule(String oidSchedule, Date datePara, Integer interval) {
        ScheduleLog scheduleLog = new ScheduleLog();
        scheduleLog.setOidSchedule(oidSchedule);
        scheduleLog.setStatus(ScheduleStatus.PENDING);
        if (interval != null) {
            scheduleLog.setScheduleDate(getScheduleDate(datePara, interval));
        } else {
            scheduleLog.setScheduleDate(datePara);
        }
        scheduleLogRepository.save(scheduleLog);
    }

    private Date getScheduleDate(Date initialDate, Integer interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialDate);
        calendar.add(Calendar.HOUR, interval);
        return calendar.getTime();
    }

    @Transactional
    public void markScheduleTaken(String oid, Boolean taken) {
        ScheduleLog scheduleLog = scheduleLogRepository.findByOid(oid);
        scheduleLog.setTaken(BooleanUtils.isTrue(taken));
        scheduleLog.setDateTaken(scheduleLog.getScheduleDate() != null ? scheduleLog.getScheduleDate() : new Date());
        scheduleLog.setStatus(BooleanUtils.isTrue(taken) ? ScheduleStatus.TAKEN : ScheduleStatus.NOT_TAKEN);
        scheduleLogRepository.update(scheduleLog);
            if (BooleanUtils.isTrue(taken)) {
            registerNextSchedule(scheduleLog.getOidSchedule(), scheduleLog.getDateTaken(), scheduleLogRepository.getIntervalByOidScheduleLog(oid));
        } else {
            ScheduleLog nextSchedule = scheduleLogRepository.findNextSchedule(oid);
            if (nextSchedule != null) {
                scheduleLogRepository.delete(nextSchedule);
            }
        }
    }



}
