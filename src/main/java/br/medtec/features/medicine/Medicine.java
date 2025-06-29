package br.medtec.features.medicine;

import br.medtec.features.symptom.Symptom;
import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "medicine")
public class Medicine extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_oid", insertable = false, updatable = false)
    private Manufacturer manufacturer;

    @Column(name = "manufacturer_oid")
    private String oidManufacturer;

    @Column(name = "dosage")
    private Double dosage;

    @Column(name = "dosage_type")
    @Enumerated(EnumType.ORDINAL)
    private DosageType dosageType;

    @Column(name = "description")
    private String description;

    @Column(name = "medicine_category", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private MedicineCategory medicineCategory;

    @Column(name = "pharmaceutical_form", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PharmaceuticalForm pharmaceuticalForm;

    @Column(name = "content")
    private Double content;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "image_path")
    private String imagePath;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="symptom_medicine", joinColumns=
            {@JoinColumn(name="medicine_oid")}, inverseJoinColumns=
            {@JoinColumn(name="symptom_oid")})
    private List<Symptom> symptoms;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="side_effect", joinColumns=
            {@JoinColumn(name="medicine_oid")}, inverseJoinColumns=
            {@JoinColumn(name="symptom_oid")})
    private List<Symptom> sideEffects;

    @Transient
    private String imageBase64;

    public enum MedicineCategory {
        ANALGESIC(0, "Analgésico", "#009688"),
        ANTIBIOTIC(1, "Antibiótico", "#ff6600"),
        ANTIALERGIC(2, "Antialérgico", "#ffc107"),
        ANTIINFLAMMATORY(3, "Antiinflamatório", "#e91e63"),
        ANTIVIRAL(4, "Antiviral", "#3f51b5"),
        ANXIOLYTIC(5, "Ansiolítico", "#9c27b0"),
        HYPNOTIC(6, "Hipnótico / Sedativo", "#673ab7"),
        ANTIDEPRESSANT(7, "Antidepressivo", "#2196f3"),
        ANTIHYPERTENSIVE(8, "Antihipertensivo", "#4caf50"),
        ANTIDIABETIC(9, "Antidiabético", "#8bc34a"),
        ANTICOAGULANT(10, "Anticoagulante / Antiagregante", "#f44336"),
        ANTIEMETIC(11, "Antiemético", "#ff9800"),
        LAXATIVE(12, "Laxante", "#795548"),
        ANTIPSYCHOTIC(13, "Antipsicótico", "#607d8b"),
        CONTRACEPTIVE(14, "Anticoncepcional", "#c2185b"),
        ANTINEOPLASTIC(15, "Antineoplásico / Quimioterápico", "#b71c1c");

        private final String description;
        private Integer value;
        @Getter
        private final String color;

        MedicineCategory(Integer value, String description) {
            this(value, description, "#007bff");
        }

        MedicineCategory(Integer value, String description, String color) {
            this.value = value;
            this.description = description;
            this.color = color;
        }

        public static MedicineCategory valueOf(Integer value) {
            if (value == null) {
                return null;
            }
            MedicineCategory[] types = MedicineCategory.values();
            for (MedicineCategory type : types) {
                if (Integer.valueOf(type.ordinal()).equals(value)) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.description;
        }
    }

        public enum PharmaceuticalForm {
            TABLET(1, "Comprimido"),
            CAPSULE(2, "Cápsula"),
            ORAL_DROPS(3, "Gotas Orais"),
            INJECTION(4, "Injeção"),
            OINTMENT(5, "Pomada"),
            CREAM(6, "Creme"),
            GEL(7, "Gel"),
            SYRUP(8, "Xarope"),
            SUPPOSITORY(9, "Supositório"),
            SPRAY(10, "Spray"),
            PATCH(11, "Adesivo"),
            POWDER(12, "Pó"),
            SOLUTION(13, "Solução"),
            SUSPENSION(14, "Suspensão");

        private final String description;
        private Integer value;

        PharmaceuticalForm(Integer value, String description) {
            this.value = value;
            this.description = description;
        }

        public static PharmaceuticalForm valueOf(Integer value) {
            PharmaceuticalForm[] types = PharmaceuticalForm.values();
            for (PharmaceuticalForm type : types) {
                if (Integer.valueOf(type.ordinal()).equals(value)) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.description;
        }
    }

    public enum DosageType {
        MG(1, "Miligramas"),
        G(2, "Gramas"),
        MCG(3, "Microgramas"),
        ML(4, "Mililitros"),
        DROPS(5, "Gotas"),
        IU(6, "Unidades Internacionais"),
        UNIT(7, "Unidades"),
        PERCENT(8, "Porcentagem");

        private final String description;
        private Integer value;

        DosageType(Integer value, String description) {
            this.value = value;
            this.description = description;
        }

        public static DosageType valueOf(Integer value) {
            DosageType[] types = DosageType.values();
            for (DosageType type : types) {
                if (Integer.valueOf(type.ordinal()).equals(value)) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return this.description;
        }
    }
}
