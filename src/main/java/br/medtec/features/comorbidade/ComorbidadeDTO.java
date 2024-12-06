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

    public Comorbidade toEntity() {
        return toEntity(new Comorbidade());
    }

    public Comorbidade toEntity(Comorbidade comorbidade) {
        comorbidade.setTipo(this.tipo);
        comorbidade.setNome(this.nome);
        comorbidade.setOid(this.oid);
        comorbidade.setDescricao(this.descricao);
    }


}
