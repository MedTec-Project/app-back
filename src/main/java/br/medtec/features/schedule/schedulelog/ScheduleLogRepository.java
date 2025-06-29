package br.medtec.features.schedule.schedulelog;

import br.medtec.generics.GenericRepository;
import org.jboss.resteasy.annotations.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleLogRepository extends GenericRepository<ScheduleLog> {
     List<ScheduleLogDTO> findToday();
     List<ScheduleLogDTO> findGeneral();
     Integer getIntervalByOidScheduleLog(String oidSchedule);
     ScheduleLog findNextSchedule(String oidScheduleLog);
     List<ScheduleLogDTO> findSchedulesNotfication(LocalDateTime now, LocalDateTime nowWith10minPlus);
}
