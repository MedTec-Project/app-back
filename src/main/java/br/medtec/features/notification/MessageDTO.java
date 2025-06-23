package br.medtec.features.notification;

import br.medtec.utils.UtilDate;
import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {

    private String oid;
    private String message;
    private String timestamp;

    public MessageDTO(String oid, String message, Date timestamp) {
        this.oid = oid;
        this.message = message;
        this.timestamp = UtilDate.formatTimestamp(timestamp);
    }

    public MessageDTO(String oid, String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
        this.oid = oid;
    }

    public MessageDTO(String message) {
        this.message = message;
    }
}
