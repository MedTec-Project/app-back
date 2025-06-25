package br.medtec.features.calendar;

import br.medtec.utils.UtilDate;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class RegisterEventDTO {

    private String oid;
    private String title;
    private String scheduleDate;
    private String description;
    private String imageBase64;

    public RegisterEventDTO(String oid, String title, Timestamp date, String description) {
        this.oid = oid;
        this.title = title;
        this.scheduleDate = UtilDate.transformDate(date);
        this.description = description;
    }

    public RegisterEventDTO() {}


    public Event toEntity() {
        return toEntity(new Event());
    }

    public Event toEntity(Event event) {
        event.setTitle(title);
        event.setDate(UtilDate.getDateByString(scheduleDate));
        event.setDescription(description);
        return event;
    }

    public EventDTO toEvent() {
        return new EventDTO(oid, EventType.EVENT, title, scheduleDate, description);
    }


}
