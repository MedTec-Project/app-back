package br.medtec.features.calendar;

import lombok.Getter;

public enum EventType {
    APPOINTMENT("#6D9FFF", "Consulta"),
    SCHEDULE_LOG("#FF4747", "Lembrete"),
    EVENT("#F9F133", "Evento");

    @Getter
    private final String color;
    @Getter
    private final String text;

    EventType(String color, String text) {
        this.color = color;
        this.text = text;
    }
}
