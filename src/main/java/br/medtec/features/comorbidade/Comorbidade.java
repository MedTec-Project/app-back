package br.medtec.features.comorbidade;

import br.medtec.generics.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "comorbidade")
@EqualsAndHashCode(callSuper = false)
public class Comorbidade extends BaseEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descricao")
    private String descricao;
}
