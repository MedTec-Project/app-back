package br.medtec.features.appointment;

import br.medtec.generics.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
@EqualsAndHashCode(callSuper = true, of = "oid")
public class Appointment extends BaseEntity {

    @Column(name = "date")
    private Date date;

    @Column(name = "oid_doctor")
    private String oidDoctor;

    @Column(name = "symptoms")
    private String symptoms;

}
