package br.medtec.features.schedule;

import lombok.Getter;

@Getter
public enum ScheduleStatus {
    PENDING("Pendente"),
    TAKEN("Consumido"),
    CANCELED("Cancelado"),
    EXPIRED("Expirado"),
    NOT_TAKEN("Não Consumido"),
    TAKEN_WITH_DELAY("Consumido com atraso");

    private final String description;

    ScheduleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ScheduleStatus fromValue(int value) {
        for (ScheduleStatus status : ScheduleStatus.values()) {
            if (status.ordinal() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

}
