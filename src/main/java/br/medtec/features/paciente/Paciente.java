package br.medtec.features.paciente;

import br.medtec.generics.Pessoa;
import br.medtec.features.medico.Medico;
import br.medtec.features.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "paciente")
@Inheritance(strategy = InheritanceType.JOINED)
public class Paciente extends Pessoa {

    @Column(name = "oid_cuidador")
    private String oidCuidador;

    @JoinColumn(name = "oid_cuidador", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario cuidador;

    @ManyToMany(mappedBy = "pacientes")
    List<Medico> medicos;


}
