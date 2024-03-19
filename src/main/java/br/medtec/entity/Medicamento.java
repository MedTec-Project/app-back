package br.medtec.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "medicamento")
public class Medicamento extends MedEntity {

    @Column(name = "nome")
    private String nome;

    @Column(name = "oid_fabricante")
    private String oidFabricante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_fabricante")
    private Fabricante fabricante;

    @Column(name = "dosagem")
    private Integer dosagem;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria_medicamento")
    @Enumerated(EnumType.STRING)
    private CategoriaMedicamento categoriaMedicamento ;
    @Column(name = "forma_farmaceutica")
    @Enumerated(EnumType.STRING)
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
        ANALGESICO("Analgesico"),
        ANTIBIOTICO("Antibiotico"),
        ANTIALERGICO("Antialérgico");

        private final String descricao;

        CategoriaMedicamento(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }

    public enum FormaFarmaceutica {
        COMPRIMIDO("Comprimido"),
        CAPSULA("Capsula"),
        XAROPE("Xarope");

        private final String descricao;

        FormaFarmaceutica(String descricao) {
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }

}
