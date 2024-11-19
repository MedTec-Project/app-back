package br.medtec.generics;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pessoa")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends MedEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email_contato")
    private String emailContato;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cpf")
    private String cpf;
}
