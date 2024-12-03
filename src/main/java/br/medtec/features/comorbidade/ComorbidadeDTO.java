package br.medtec.features.comorbidade;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ComorbidadeDTO {
    private String oid;
    private String nome;
    private String tipo;
    private String descricao;


}
