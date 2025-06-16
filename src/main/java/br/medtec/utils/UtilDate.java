package br.medtec.utils;

import br.medtec.exceptions.MEDExecption;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class UtilDate {

    public static Date getDateByString(String date) {
       try {
           if (date == null) {
               return null;
           }
           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
           dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
           return dateFormat.parse(date);
       } catch (ParseException e) {
           log.error("Erro ao converter data", e);
           throw new MEDExecption("Erro ao converter data");
       }
    }

    public static String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return dateFormat.format(date);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return dateFormat.format(date);
    }

    public static String formatTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return dateFormat.format(date);
    }
}
