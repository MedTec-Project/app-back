package br.medtec.features.schedule;

import br.medtec.features.doctor.Doctor;
import br.medtec.features.medicine.Medicine;
import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
    private Integer interval;

    @Column(name = "reminder")
    private String reminder;
}
