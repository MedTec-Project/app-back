package br.medtec.medico;

import br.medtec.entity.Paciente;
import br.medtec.entity.Pessoa;
import jakarta.persistence.*;

@Entity
@Table(name = "medico")
public class Medico extends Pessoa {

    @Column(name = "crm")
    private String crm;

    @Column(name = "oid_paciente")
    private String oid_paciente;

    @JoinColumn(name = "oid_paciente", insertable = false, updatable = false)
    @ManyToOne
    private Paciente paciente;

}
