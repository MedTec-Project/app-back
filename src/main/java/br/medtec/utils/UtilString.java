package br.medtec.utils;

public class UtilString {
    public static boolean stringValida(String str) {
        return (str != null) && (!(str).trim().isEmpty());
    }

    public static boolean validarTelefone(String str){
        return str.length() == 11;
    }

    public static boolean validarEmail(String str) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return str.matches(emailRegex);
    }

}
