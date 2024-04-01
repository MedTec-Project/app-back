package br.medtec.utils;

public class UtilString {
    public static boolean stringValida(String str) {
        return (str != null) && (!(str).trim().isEmpty());
    }

}
