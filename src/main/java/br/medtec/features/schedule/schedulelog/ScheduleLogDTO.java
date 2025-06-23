package br.medtec.features.schedule.schedulelog;

import br.medtec.features.medicine.Medicine;
import br.medtec.features.schedule.ScheduleStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class ScheduleLogDTO {

    private String oid;
    private String oidSchedule;
    private Boolean taken;
    private String dateTakenString;
    private Date dateTaken;
    private Date scheduleDate;
    private ScheduleStatus status;
    private String imagePath;
    private String imageBase64;
    private String medicineName;
    private Double dosage;
    private Medicine.DosageType dosageType;
    private Medicine.PharmaceuticalForm pharmaceuticalForm;
    private Double content;
    private Medicine.MedicineCategory medicineCategory;

    public ScheduleLogDTO(String oid, String oidSchedule, Boolean taken, Byte status, Timestamp scheduleDate, String imagePath, String medicineName, Double dosage, Byte dosageType, Byte pharmaceuticalForm, Double content, Byte medicineCategory) {
        this.oid = oid;
        this.oidSchedule = oidSchedule;
        this.status = ScheduleStatus.fromValue(status);
        this.scheduleDate = scheduleDate;
        this.taken = taken;
        this.imagePath = imagePath;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.dosageType = Medicine.DosageType.valueOf(Integer.valueOf(dosageType));
        this.pharmaceuticalForm = Medicine.PharmaceuticalForm.valueOf(Integer.valueOf(pharmaceuticalForm));
        this.content = content;
        this.medicineCategory = Medicine.MedicineCategory.valueOf(Integer.valueOf(medicineCategory));

    }

    public ScheduleLogDTO(String oid, String oidSchedule, String medicineName, Date scheduleDate) {
        this.scheduleDate = scheduleDate;
        this.oid = oid;
        this.oidSchedule = oidSchedule;
        this.medicineName = medicineName;
    }


}
