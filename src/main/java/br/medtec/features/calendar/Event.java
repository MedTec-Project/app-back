package br.medtec.features.calendar;

import br.medtec.generics.BaseEntity;
import br.medtec.utils.UtilDate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Table(name = "event")
@Data
@EqualsAndHashCode(callSuper = true, of = {"oid"})
public class Event extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    public RegisterEventDTO toDTO() {
        RegisterEventDTO dto = new RegisterEventDTO();
        dto.setOid(this.getOid());
        dto.setTitle(title);
        dto.setScheduleDate(UtilDate.transformDate(date));
        dto.setDescription(description);
        return dto;
    }
}
