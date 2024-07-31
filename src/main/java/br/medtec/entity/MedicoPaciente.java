package br.medtec.entity;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.medico.Medico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "medico_paciente")
public class MedicoPaciente extends MedEntity {

        @Column(name = "oid_medico")
        private String oidMedico;

        @JoinColumn(name = "oid_medico", insertable = false, updatable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private Medico medico;

        @Column(name = "oid_paciente")
        private String oidPaciente;

        @JoinColumn(name = "oid_paciente", insertable = false, updatable = false)
        @ManyToOne(fetch = FetchType.LAZY)
        private Paciente paciente;


}
