package br.medtec.features.schedule.schedulelog;

import br.medtec.features.schedule.Schedule;
import br.medtec.features.schedule.ScheduleStatus;
import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "schedule_log")
public class ScheduleLog extends BaseEntity {

    @Column(name = "oid_schedule", length = 50, nullable = false)
    private String oidSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_schedule", referencedColumnName = "oid", insertable = false, updatable = false)
    private Schedule schedule;

    @Column(name = "taken")
    private Boolean taken;

    @Column(name = "date_taken")
    private Date dateTaken;

    @Column(name = "schedule_date", nullable = false)
    private Date scheduleDate;

    @Column(name = "schedule_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ScheduleStatus status;

    @Column(name = "notification_sent")
    private Boolean notificationSent = false;

    public ScheduleLog() {
        super();
    }

    public ScheduleLog(String oidSchedule, Boolean taken, Date dateTaken, ScheduleStatus status) {
        this.oidSchedule = oidSchedule;
        this.taken = taken;
        this.dateTaken = dateTaken;
        this.status = status;
    }

}
