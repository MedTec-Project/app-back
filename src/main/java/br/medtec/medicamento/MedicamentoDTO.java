package br.medtec.medicamento;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Data
public class MedicamentoDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "Dipirona")
    private String nome;

    @Schema(example = "500")
    private Double dosagem;

    @Schema(example = "1")
    private Integer tipoDosagem;

    @Schema(example = "Analgésico")
    private String descricao;

    @Schema(example = "123456")
    private Integer numeroRegistro;

    @Schema(example = "bffe7665-344f-4d72-81bc-f30288e48f81")
    private String oidFabricante;

    @Schema(example = "1")
    private Integer categoriaMedicamento;

    @Schema(example = "1")
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
    @Schema(name = "Sintoma")
    public static class SintomaDTO {

        @Schema(hidden = true)
        private String oid;

        @Schema(example = "Dor de cabeça")
        private String nome;

        public Sintoma toEntity() {
            Sintoma sintoma = new Sintoma();
            sintoma.setNome(this.nome);
            return sintoma;
        }
    }
}
