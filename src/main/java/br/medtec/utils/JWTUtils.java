package br.medtec.utils;

import br.medtec.features.user.User;
import io.smallrye.jwt.auth.principal.DefaultJWTParser;
import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Date;
import java.util.Set;

@ApplicationScoped
public class JWTUtils {

    public JWTUtils(JWTAuthContextInfo jwtAuthContextInfo) {
        jwtParser = new DefaultJWTParser(jwtAuthContextInfo);
    }

    JWTParser jwtParser;

    public static String generateToken(User userDTO) {
        Date date = new Date();
        long time = date.getTime();
        long exp = time + 1000L * 60 * 60 * 24 * 30;
        return generateToken(userDTO, exp);
    }

    public static String generateToken(User user, long time) {
        Date date = new Date();
        long exp = date.getTime() + time;
        return Jwt.issuer("https://medtec.com.br/issuer")
                .upn(user.getEmail())
                .groups(BooleanUtils.isTrue(user.getAdmin()) ? "admin" : "user")
                .claim("name", user.getName())
                .claim("phone", user.getPhone())
                .claim("oidUser", user.getOid())
                .expiresAt(exp)
                .sign();
    }

    public String getClaim(String claim) {
        try {
            JsonWebToken jwt = jwtParser.parse(UserSession.getInstance().getToken());
            return jwt.getClaim(claim);
        } catch (ParseException e) {
            return null;
        }
    }

    public Set<String> getGroups() {
        try {
            JsonWebToken jwt = jwtParser.parse(UserSession.getInstance().getToken());
            return jwt.getGroups();
        } catch (ParseException e) {
            return null;
        }
    }

    public void verifyToken(String token) throws Exception {
        jwtParser.parse(token);
    }
}
