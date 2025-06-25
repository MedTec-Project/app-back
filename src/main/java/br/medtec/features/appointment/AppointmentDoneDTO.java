package br.medtec.features.appointment;

import lombok.Data;
import lombok.Getter;

@Data
public class AppointmentDoneDTO {

    private boolean done;

    public AppointmentDoneDTO() {
    }

    public AppointmentDoneDTO(boolean done) {
        this.done = done;
    }
}
