package br.medtec.utils;

public class UtilString {
    public static boolean stringValida(String str) {
        return (str != null) && (!(str).trim().isEmpty());
    }

    public static boolean validarTelefone(String str){
        return str.length() == 11;
    }

    public static boolean validarEmail(String str) {
        return (str.indexOf("@") > 0) && (str.indexOf(".com") > 0);
    }

}
