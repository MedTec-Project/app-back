package br.medtec.features.calendar;

import br.medtec.utils.UtilDate;
import lombok.Data;

import java.util.Date;

@Data
public class EventDTO {

    private String oid;
    private String title;
    private String date;
    private String description;
    private EventType type;
    private String color;

    public EventDTO(String oid, EventType type, String title, Date date, String description) {
        this.oid = oid;
        this.title = title;
        this.date = UtilDate.transformDate(date);
        this.description = description;
        this.type = type;
        this.color = type.getColor();
    }

    public EventDTO(String oid, EventType type, String title, String date, String description) {
        this.oid = oid;
        this.title = title;
        this.date = date;
        this.description = description;
        this.type = type;
        this.color = type.getColor();
    }



}
