package br.medtec.features.medicine;

import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "manufacturer")
public class Manufacturer extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "tradeName")
    private String tradeName;

    @Column(name = "cnpj")
    private String cnpj;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manufacturer")
    private List<Medicine> medicines;

}
