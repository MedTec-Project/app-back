package br.medtec.utils;

import lombok.Setter;

import java.util.Objects;

@Setter
public class UserSession {

    private static UserSession instance;

    private String oidUser;

    private String userType;

    private String token;

    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public static String getOidUser() {
            return Objects.requireNonNullElse(getInstance().oidUser, "user");
    }

    public static String getUserType() {
        return getInstance().userType;
    }

    public static String getToken() {
        return getInstance().token;
    }
}
