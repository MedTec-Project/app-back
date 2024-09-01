<<<<<<<< HEAD:src/main/java/br/medtec/generics/Pessoa.java
package br.medtec.generics;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.UtilString;
========
package br.medtec.usuario;

import br.medtec.generic.MedEntity;
>>>>>>>> 2cb6c86 (refactor(estrutura): refatorado as estrutura das pasta para minimizar a quantidade de pastas.):src/main/java/br/medtec/features/usuario/Pessoa.java
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pessoa")
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends MedEntity {

    @Column(name = "nome", nullable = false)
    @NotNull
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
