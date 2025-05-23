package br.medtec.features.medicine;

import br.medtec.features.symptom.Symptom;
import io.netty.handler.codec.http.multipart.FileUpload;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@Data
public class MedicineDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "Dipyrone")
    private String name;

    @Schema(example = "500")
    private Double dosage;

    @Schema(example = "1")
    private Integer dosageType;

    private String dosageTypeName;

    @Schema(example = "Analgesic")
    private String description;

    @Schema(example = "123456")
    private String registrationNumber;

    @Schema(example = "bffe7665-344f-4d72-81bc-f30288e48f81")
    private String oidManufacturer;

    @Schema(example = "1")
    private Integer medicineCategory;

    private String medicineCategoryName;

    private String medicineCategoryColor;

    @Schema(example = "1")
    private Integer pharmaceuticalForm;

    private String pharmaceuticalFormName;

    @Schema(example = "20")
    private Double content;

    @Schema(description = "Imagem do medicamento em base64")
    private String imageBase64;

    private String imagePath;

    private List<SymptomDTO> symptoms;

    private List<SymptomDTO> sideEffects;

    public MedicineDTO() {
        this.symptoms = List.of();
        this.sideEffects = List.of();
    }

    public MedicineDTO(String oid, String name, Double dosage, Medicine.DosageType dosageType, Medicine.PharmaceuticalForm pharmaceuticalForm, String imagePath, Medicine.MedicineCategory medicineCategory, Double content) {
        this.oid = oid;
        this.name = name;
        this.dosage = dosage;
        this.dosageTypeName = dosageType == null ? null : dosageType.name();
        this.pharmaceuticalFormName = pharmaceuticalForm == null ? null : pharmaceuticalForm.toString();
        this.imagePath = imagePath;
        this.content = content;
        this.medicineCategoryName = medicineCategory == null ? null : medicineCategory.toString();
        this.medicineCategoryColor = medicineCategory == null ? null : medicineCategory.getColor();
    }

    public Medicine toEntity() {
        Medicine medicine = new Medicine();
        return this.toEntity(medicine);
    }

    public Medicine toEntity(Medicine medicine) {
        medicine.setName(this.name);
        medicine.setDosage(this.dosage);
        medicine.setDosageType(Medicine.DosageType.valueOf(this.dosageType));
        medicine.setDescription(this.description);
        medicine.setRegistrationNumber(this.registrationNumber);
        medicine.setOidManufacturer(this.oidManufacturer);
        medicine.setMedicineCategory(Medicine.MedicineCategory.valueOf(this.medicineCategory));
        medicine.setPharmaceuticalForm(Medicine.PharmaceuticalForm.valueOf(this.pharmaceuticalForm));
        medicine.setSideEffects(this.sideEffects.stream().map(SymptomDTO::toEntity).toList());
        medicine.setSymptoms(this.symptoms.stream().map(SymptomDTO::toEntity).toList());
        medicine.setContent(this.content);
        return medicine;
    }

    @Data
    @Schema(name = "Symptom")
    public static class SymptomDTO {

        @Schema(hidden = true)
        private String oid;

        @Schema(example = "Headache")
        private String name;

        public Symptom toEntity() {
            Symptom symptom = new Symptom();
            symptom.setName(this.name);
            return symptom;
        }
    }
}
