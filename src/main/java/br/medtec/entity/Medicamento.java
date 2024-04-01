package br.medtec.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "medicamento")
public class Medicamento extends MedEntity {

    @Column(name = "nome")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_fabricante")
    private Fabricante fabricante;

    @Column(name = "dosagem")
    private Integer dosagem;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria_medicamento")
    @Enumerated(EnumType.ORDINAL)
    private CategoriaMedicamento categoriaMedicamento ;
    @Column(name = "forma_farmaceutica")
    @Enumerated(EnumType.ORDINAL)
    private FormaFarmaceutica formaFarmaceutica;

    @Column(name = "numero_registro")
    private Integer numeroRegistro;

    @ManyToMany
    @JoinTable(name="sintoma_medicamento", joinColumns=
            {@JoinColumn(name="oid_medicamento")}, inverseJoinColumns=
            {@JoinColumn(name="oid_sintoma")})
    private List<Sintoma> sintomas;

    @ManyToMany
    @JoinTable(name="efeito_colateral", joinColumns=
            {@JoinColumn(name="oid_medicamento")}, inverseJoinColumns=
            {@JoinColumn(name="oid_sintoma")})
    private List<Sintoma> efeitosColaterais;



    public enum CategoriaMedicamento {
        ANALGESICO(0,"Analgesico"),
        ANTIBIOTICO(1, "Antibiotico"),
        ANTIALERGICO(2, "Antialérgico");

        private final String descricao;
        private Integer value;

        CategoriaMedicamento(Integer value, String descricao) {
            this.value = value;
            this.descricao = descricao;
        }

        public static CategoriaMedicamento valueOf(Integer valor) {
            CategoriaMedicamento[] tipos = CategoriaMedicamento.values();
            for (CategoriaMedicamento tipo : tipos) {
                if (Integer.valueOf(tipo.ordinal()).equals(valor)) {
                    return tipo;
                }
            }
            return null;
        }


        @Override
        public String toString() {
            return this.descricao;
        }
    }

    public enum FormaFarmaceutica {
        COMPRIMIDO(0, "Comprimido"),
        CAPSULA(1,"Capsula"),
        XAROPE(2,"Xarope");

        private final String descricao;
        private Integer value;

        FormaFarmaceutica(Integer value, String descricao) {
            this.value = value;
            this.descricao = descricao;
        }

        public static FormaFarmaceutica valueOf(Integer valor) {
            FormaFarmaceutica[] tipos = FormaFarmaceutica.values();
            for (FormaFarmaceutica tipo : tipos) {
                if (Integer.valueOf(tipo.ordinal()).equals(valor)) {
                    return tipo;
                }
            }
            return null;
        }


        @Override
        public String toString() {
            return this.descricao;
        }
    }

}
