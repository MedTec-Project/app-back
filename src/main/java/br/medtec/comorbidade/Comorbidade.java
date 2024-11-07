package br.medtec.comorbidade;

import br.medtec.entity.MedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "comorbidade")
@EqualsAndHashCode(callSuper = false)
public class Comorbidade extends MedEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descricao")
    private String descricao;
}
