package br.medtec.features.symptom;


import br.medtec.features.medicine.Medicine;
import br.medtec.generics.BaseEntity;
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
public class Symptom extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "symptoms")
    private List<Medicine> medicines;

//    @ManyToMany(mappedBy = "efeitosColaterais")
//    private List<Medicamento> medicamentosEfeitoColateral;

}
