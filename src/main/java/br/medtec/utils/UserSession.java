package br.medtec.utils;

import br.medtec.features.user.User;
import jakarta.inject.Singleton;
import lombok.Setter;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Objects;

@Singleton
public class UserSession {

    private static UserSession instance;

    private String oidUser;

    private String userType;

    private String token;

    private UserSession() {
    }


    public static void setOidUser(String oidUser) {
        UserSession session = getInstance();
        session.oidUser = oidUser;
    }

    public static void setUserType(String userType) {
        UserSession session = getInstance();
        session.userType = userType;
    }

    public static void setToken(String token) {
        UserSession session = getInstance();
        session.token = token;
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

    public static void setSession(User user) {
        if (user == null) {
            return;
        }
        UserSession session = getInstance();
        session.oidUser = user.getOid();
        session.userType = BooleanUtils.isTrue(user.getAdmin()) ? "admin" : "user";
        session.token = JWTUtils.generateToken(user);
    }
}
