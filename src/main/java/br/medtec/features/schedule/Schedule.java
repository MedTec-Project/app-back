package br.medtec.features.schedule;

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

    @Column(name = "oid_doctor")
    @Temporal(TemporalType.DATE)
    private String oidDoctor;

    @Column(name = "initial_date")
    @Temporal(TemporalType.DATE)
    private Date initialDate;

    @Column(name = "final_date")
    @Temporal(TemporalType.DATE)
    private Date finalDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "interval")
    private Integer interval;

    @Column(name = "reminder")
    private String reminder;
}
