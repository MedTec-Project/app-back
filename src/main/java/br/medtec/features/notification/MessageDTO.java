package br.medtec.features.notification;

import br.medtec.utils.UtilDate;
import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {

    private String message;
    private String timestamp;

    public MessageDTO(String message, Date timestamp) {
        this.message = message;
        this.timestamp = UtilDate.formatTimestamp(timestamp);
    }

    public MessageDTO(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public MessageDTO(String message) {
        this.message = message;
    }
}
