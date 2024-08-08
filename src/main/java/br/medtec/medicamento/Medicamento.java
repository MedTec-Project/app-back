package br.medtec.medicamento;

import br.medtec.entity.Fabricante;
import br.medtec.entity.MedEntity;
import br.medtec.entity.Sintoma;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "medicamento")
public class Medicamento extends MedEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_fabricante", insertable = false, updatable = false)
    private Fabricante fabricante;

    @Column(name = "oid_fabricante")
    private String oidFabricante;

    @Column(name = "dosagem")
    private Double dosagem;

    @Column(name = "tipoDosagem")
    @Enumerated(EnumType.ORDINAL)
    private TipoDosagem tipoDosagem;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "categoria_medicamento", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CategoriaMedicamento categoriaMedicamento ;

    @Column(name = "forma_farmaceutica", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private FormaFarmaceutica formaFarmaceutica;

    @Column(name = "numero_registro")
    private Integer numeroRegistro;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="sintoma_medicamento", joinColumns=
            {@JoinColumn(name="oid_medicamento")}, inverseJoinColumns=
            {@JoinColumn(name="oid_sintoma")})
    private List<Sintoma> sintomas;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
            if (valor == null) {
                return null;
            }
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

    public enum TipoDosagem {
        ML(0, "ML"),
        MG(1,"MG"),
        G(2,"G");

        private final String descricao;
        private Integer value;

        TipoDosagem(Integer value, String descricao) {
            this.value = value;
            this.descricao = descricao;
        }

        public static TipoDosagem valueOf(Integer valor) {
            TipoDosagem[] tipos = TipoDosagem.values();
            for (TipoDosagem tipo : tipos) {
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
