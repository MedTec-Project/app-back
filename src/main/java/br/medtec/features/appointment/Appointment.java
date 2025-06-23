package br.medtec.features.appointment;

import br.medtec.features.doctor.Doctor;
import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
@EqualsAndHashCode(callSuper = true, of = "oid")
public class Appointment extends BaseEntity {

    @Column(name = "schedule_date")
    private Date scheduleDate;

    @Column(name = "oid_doctor")
    private String oidDoctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_doctor", insertable = false, updatable = false)
    private Doctor doctor;

    @Column(name = "symptoms")
    private String symptoms;

    @Column(name = "reminder")
    private String reminder;

    @Column(name = "notification_sent")
    private Boolean notificationSent = false;

    @Column(name = "done")
    private Boolean done = false;

}
