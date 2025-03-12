package br.medtec.features.symptom;


import br.medtec.features.medicamento.Medicamento;
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
@Table(name = "symptom")
public class Symptom extends MedEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "sintomas")
    private List<Medicamento> medicines;

//    @ManyToMany(mappedBy = "efeitosColaterais")
//    private List<Medicamento> medicamentosEfeitoColateral;

}
