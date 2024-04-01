package br.medtec.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "fabricante")
public class Fabricante extends MedEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "nomeFantasia")
    private String nomeFantasia;

    @Column(name = "cnpj")
    private String cnpj;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fabricante")
    private List<Medicamento> medicamentos;



}
