package br.medtec.medicamento;

import br.medtec.medicamento.Medicamento;
import br.medtec.entity.Sintoma;
import lombok.Data;

import java.util.List;

@Data
public class MedicamentoDTO {
    private String oid;
    private String nome;
    private Double dosagem;
    private Integer tipoDosagem;
    private String descricao;
    private Integer numeroRegistro;
    private String oidFabricante;
    private Integer categoriaMedicamento;
    private Integer formaFarmaceutica;
    private List<SintomaDTO> sintomas;
    private List<SintomaDTO> efeitosColaterais;

    public MedicamentoDTO() {
        this.sintomas = List.of();
        this.efeitosColaterais = List.of();
    }

    public Medicamento toEntity() {
        Medicamento medicamento = new Medicamento();
        return this.toEntity(medicamento);
    }

    public Medicamento toEntity(Medicamento medicamento) {
        medicamento.setNome(this.nome);
        medicamento.setDosagem(this.dosagem);
        medicamento.setTipoDosagem(Medicamento.TipoDosagem.valueOf(this.tipoDosagem));
        medicamento.setDescricao(this.descricao);
        medicamento.setNumeroRegistro(this.numeroRegistro);
        medicamento.setOidFabricante(this.oidFabricante);
        medicamento.setCategoriaMedicamento(Medicamento.CategoriaMedicamento.valueOf(this.categoriaMedicamento));
        medicamento.setFormaFarmaceutica(Medicamento.FormaFarmaceutica.valueOf(this.formaFarmaceutica));
        medicamento.setEfeitosColaterais(this.efeitosColaterais.stream().map(SintomaDTO::toEntity).toList());
        medicamento.setSintomas(this.sintomas.stream().map(SintomaDTO::toEntity).toList());
        return medicamento;
    }


    @Data
    public static class SintomaDTO {
        private String oid;
        private String nome;

        public Sintoma toEntity() {
            Sintoma sintoma = new Sintoma();
            sintoma.setNome(this.nome);
            return sintoma;
        }
    }
}
