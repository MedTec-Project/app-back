package br.medtec.utils;

public class UtilString {
    public static boolean stringValida(String str) {
        return (str != null) && (!(str).trim().isEmpty());
    }

    public static boolean validarNull(String str){
        return str == null;
    }

    public static boolean validarTelefone(String str){
        return str != null && str.length() == 11;
    }

    public static boolean validarTelefoneOrNull(String str){
         return validarNull(str) || str.length() == 11;
    }

    public static boolean validarEmail(String str) {
        if (str == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return str.matches(emailRegex);
    }

    public static boolean validarEmailOrNull(String str){
        return validarNull(str) || validarEmail(str);
    }

    public static boolean validarCpf(String str) {
        return str != null && str.length() == 11;
    }

    public static String formatarCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    public static String formatarTelefone(String telefone) {
        return telefone.replaceAll("[^0-9]", "");
    }

}
