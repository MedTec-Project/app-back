package br.medtec.utils;

import br.medtec.exceptions.MEDExecption;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class UtilDate {

    public static Date getDateByString(String date) {
       try {
           DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           return dateFormat.parse(date);
       } catch (ParseException e) {
           log.error("Erro ao converter data", e);
           throw new MEDExecption("Erro ao converter data");
       }
    }
}
