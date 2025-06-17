package br.medtec.utils;

import br.medtec.exceptions.MEDExecption;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class UtilDate {

    /**
     * Converte uma string em formato ISO 8601 (com 'Z' no final) para um objeto Date
     */
    public static Date getDateByString(String date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse(date);
        } catch (ParseException e) {
            log.error("Erro ao converter data", e);
            throw new MEDExecption("Erro ao converter data");
        }
    }

    /**
     * Formata um LocalDateTime no formato dd/MM/yyyy
     */
    public static String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.of("America/Sao_Paulo"));
        return date.format(formatter);
    }

    /**
     * Formata um Date no formato dd/MM/yyyy
     */
    public static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return dateFormat.format(date);
    }

    /**
     * Formata um Date no formato dd/MM/yyyy HH:mm:ss
     */
    public static String formatTimestamp(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return dateFormat.format(date);
    }
}
