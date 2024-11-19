package br.medtec.features.medicamento;


import br.medtec.generics.MedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "sintoma")
public class Sintoma extends MedEntity {

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "sintomas")
    private List<Medicamento> medicamentos;

//    @ManyToMany(mappedBy = "efeitosColaterais")
//    private List<Medicamento> medicamentosEfeitoColateral;

}
