package br.medtec.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "sintoma")
public class Sintoma extends MedEntity {

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "sintomas")
    private List<Medicamento> medicamentos;

//    @ManyToMany(mappedBy = "efeitosColaterais")
//    private List<Medicamento> medicamentosEfeitoColateral;

}
