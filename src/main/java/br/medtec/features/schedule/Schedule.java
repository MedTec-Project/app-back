package br.medtec.features.schedule;

import br.medtec.features.doctor.Doctor;
import br.medtec.features.medicine.Medicine;
import br.medtec.features.schedule.schedulelog.ScheduleLog;
import br.medtec.generics.BaseEntity;
import br.medtec.utils.UtilDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "schedule")
@Data
public class Schedule extends BaseEntity {

    @Column(name = "oid_medicine", nullable = false)
    private String oidMedicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_medicine", insertable = false, updatable = false)
    private Medicine medicine;

    @Column(name = "oid_doctor")
    private String oidDoctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_doctor", insertable = false, updatable = false)
    private Doctor doctor;

    @Column(name = "initial_date")
    @Temporal(TemporalType.DATE)
    private Date initialDate;

    @Column(name = "final_date")
    @Temporal(TemporalType.DATE)
    private Date finalDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "interval_medicine")
    private Integer intervalMedicine;

    @Column(name = "reminder")
    private String reminder;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    @JsonbTransient
    private List<ScheduleLog> scheduleLogs;

    public ScheduleDTO toDTO() {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setOid(this.getOid());
        scheduleDTO.setOidMedicine(this.getOidMedicine());
        scheduleDTO.setOidDoctor(this.getOidDoctor());
        scheduleDTO.setInitialDate(UtilDate.formatDate(this.getInitialDate()));
        scheduleDTO.setFinalDate(UtilDate.formatDate(this.getFinalDate()));
        scheduleDTO.setQuantity(this.getQuantity());
        scheduleDTO.setInterval(this.getIntervalMedicine());
        scheduleDTO.setReminder(this.getReminder());
        if (this.getMedicine() != null) {
            scheduleDTO.setNameMedicine(this.getMedicine().getName());
            scheduleDTO.setDosageMedicine(this.getMedicine().getDosage());
            scheduleDTO.setDosageTypeNameMedicine(this.getMedicine().getDosageType());
        }
        return scheduleDTO;
    }

}
