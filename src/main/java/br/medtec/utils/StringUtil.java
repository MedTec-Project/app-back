package br.medtec.utils;

public class StringUtil {

    public static boolean isValidString(String str) {
        return (str != null) && (!str.trim().isEmpty());
    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isValidPhone(String str) {
        return str != null && str.length() == 11;
    }

    public static boolean isValidPhoneOrNull(String str) {
        return isNull(str) || str.length() == 11;
    }

    public static boolean isValidEmail(String str) {
        if (str == null) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return str.matches(emailRegex);
    }

    public static boolean isValidEmailOrNull(String str) {
        return isNull(str) || isValidEmail(str);
    }

    public static boolean isValidCpf(String str) {
        return str != null && str.length() == 11;
    }

    public static String formatCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    public static String formatPhone(String phone) {
        return phone.replaceAll("[^0-9]", "");
    }
}
