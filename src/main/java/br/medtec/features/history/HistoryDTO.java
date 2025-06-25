package br.medtec.features.history;

import lombok.Data;

@Data
public class HistoryDTO {

    private String oid;
    private String title;
    private String description;
    private String date;
    private String type;

    public HistoryDTO(String oid, String title, String description, String date, String type) {
        this.oid = oid;
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type;
    }


}
