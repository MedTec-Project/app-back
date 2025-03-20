package br.medtec.features.medicine;

import br.medtec.features.symptom.Symptom;
import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Column(name = "registration_number")
    private Integer registrationNumber;

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

    public enum MedicineCategory {
        ANALGESIC(0,"Analgesic"),
        ANTIBIOTIC(1, "Antibiotic"),
        ANTIALERGIC(2, "Antiallergic");

        private final String description;
        private Integer value;

        MedicineCategory(Integer value, String description) {
            this.value = value;
            this.description = description;
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
