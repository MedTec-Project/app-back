package br.medtec.features.medico;

import br.medtec.features.paciente.Paciente;
import br.medtec.generics.Pessoa;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "medico")
@Data
@EqualsAndHashCode(callSuper = true)
public class Medico extends Pessoa {

    @Column(name = "crm")
    private String crm;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "medico_paciente",
            joinColumns = @JoinColumn(name = "oid_medico"),
            inverseJoinColumns = @JoinColumn(name = "oid_paciente"))
    private List<Paciente> pacientes;
}
