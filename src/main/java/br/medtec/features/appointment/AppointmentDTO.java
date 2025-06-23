package br.medtec.features.appointment;

import br.medtec.utils.UtilDate;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Timestamp;

@Data
public class AppointmentDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "Dor de cabeça, dor de garganta, garganta seca")
    private String symptoms;

    @Schema(example = "2023-01-01")
    private String scheduleDate;

    @Schema(example = "0788cee3-da13-4478-b306-1f31c9da9303")
    private String oidDoctor;

    @Schema(example = "Lembrete do agendamento")
    private String reminder;

    @Schema(hidden = true)
    private String nameDoctor;

    public Appointment toEntity() {
        return toEntity(new Appointment());
    }

    public Appointment toEntity(Appointment appointment) {
        appointment.setSymptoms(this.symptoms);
        appointment.setScheduleDate(UtilDate.getDateByString(this.scheduleDate));
        appointment.setOidDoctor(this.oidDoctor);
        appointment.setReminder(this.reminder);
       return appointment;
    }

    public AppointmentDTO() {
    }

    public AppointmentDTO(String oid, Timestamp scheduleDate, String reminder, String oidDoctor, String doctorName) {
        this.oid = oid;
        this.scheduleDate = UtilDate.formatTimestamp(scheduleDate);
        this.reminder = reminder;
        this.oidDoctor = oidDoctor;
        this.nameDoctor = doctorName;
    }

}
