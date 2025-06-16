package br.medtec.features.appointment;

import br.medtec.utils.UtilDate;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class AppointmentDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "Dor de cabeça, dor de garganta, garganta seca")
    private String symptoms;

    @Schema(example = "2023-01-01")
    private String date;

    @Schema(example = "0788cee3-da13-4478-b306-1f31c9da9303")
    private String oidDoctor;

    public Appointment toEntity() {
        return toEntity(new Appointment());
    }

    public Appointment toEntity(Appointment appointment) {
        appointment.setSymptoms(this.symptoms);
        appointment.setDate(UtilDate.getDateByString(this.date));
        appointment.setOidDoctor(this.oidDoctor);
       return appointment;
    }

}
