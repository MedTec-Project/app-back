package br.medtec.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "agendamento")
@Data
public class Agendamento extends MedEntity {

    @Column(name = "oid_medicamento", nullable = false)
    private String oidMedicamento;

    @Column(name = "oid_medico")
    @Temporal(TemporalType.DATE)
    private String oidMedico;

    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Column(name = "dataFim")
    private Date dataFim;

    @Column(name = "quantidade_medicamento")
    private Integer quantidadeMedicamento;
}
