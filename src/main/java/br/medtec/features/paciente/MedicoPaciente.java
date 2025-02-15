package br.medtec.features.paciente;

import br.medtec.generics.MedEntity;
import br.medtec.features.medico.Medico;
import jakarta.persistence.*;
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
