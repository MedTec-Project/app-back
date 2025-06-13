package br.medtec.features.schedule;

import lombok.Data;

@Data
public class ScheduleTakenDTO {

    private Boolean taken;

    public ScheduleTakenDTO() {
    }

    public ScheduleTakenDTO(Boolean taken) {
        this.taken = taken;
    }

}
