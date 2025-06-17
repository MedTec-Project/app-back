package br.medtec.features.schedule;

import br.medtec.features.medicine.Medicine;
import br.medtec.utils.UtilDate;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.text.DateFormat;
import java.util.Date;

@Data
public class ScheduleDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "0788cee3-da13-4478-b306-1f31c9da9303")
    private String oidMedicine;

    @Schema(example = "0788cee3-da13-4478-b306-1f31c9da9303")
    private String oidDoctor;

    @Schema(example = "2023-01-01")
    private String initialDate;

    @Schema(example = "2023-01-02")
    private String finalDate;

    @Schema(example = "1")
    private Integer quantity;

    @Schema(example = "1")
    private Integer interval;

    @Schema(example = "Lembrete do agendamento")
    private String reminder;

    @Schema(hidden = true)
    private String nameMedicine;

    @Schema(hidden = true)
    private Double dosageMedicine;

    @Schema(hidden = true)
    private Medicine.DosageType dosageTypeNameMedicine;

    public Schedule toEntity() {
        Schedule schedule = new Schedule();
        return this.toEntity(schedule);
    }

    public Schedule toEntity(Schedule schedule) {
        schedule.setOidMedicine(this.oidMedicine);
        schedule.setOidDoctor(this.oidDoctor);
        schedule.setInitialDate(UtilDate.getDateByString(this.initialDate));
        schedule.setFinalDate(UtilDate.getDateByString(this.finalDate));
        schedule.setQuantity(this.quantity);
        schedule.setIntervalMedicine(this.interval);
        schedule.setReminder(this.reminder);
        return schedule;
    }
}
