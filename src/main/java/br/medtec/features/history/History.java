package br.medtec.features.history;

import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "history")
@Data
@EqualsAndHashCode(callSuper = true, of = "oid")
public class History extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private HistoryType type;

    @Transient
    private String date;

    @Transient
    private String typeString;

}
