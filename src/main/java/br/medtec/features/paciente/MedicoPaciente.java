<<<<<<<< HEAD:src/main/java/br/medtec/features/paciente/MedicoPaciente.java
package br.medtec.features.paciente;

import br.medtec.generics.MedEntity;
import br.medtec.features.medico.Medico;
========
package br.medtec.medico;

import br.medtec.generic.MedEntity;
import br.medtec.entity.Paciente;
>>>>>>>> 2cb6c86 (refactor(estrutura): refatorado as estrutura das pasta para minimizar a quantidade de pastas.):src/main/java/br/medtec/features/medico/MedicoPaciente.java
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
