package br.medtec.features.comorbidade;

import br.medtec.generics.MedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "comorbidade")
@EqualsAndHashCode(callSuper = false)
@Data
public class Comorbidade extends MedEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "descricao")
    private String descricao;
}
