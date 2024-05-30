package br.medtec.dto;

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


    @Data
    public static class SintomaDTO {
        private String oid;
        private String nome;
    }
}
