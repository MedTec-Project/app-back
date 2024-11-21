package br.medtec.utils;
import br.medtec.features.usuario.Usuario;
import br.medtec.features.usuario.UsuarioDTO;
import io.smallrye.jwt.auth.principal.DefaultJWTParser;
import io.smallrye.jwt.auth.principal.JWTAuthContextInfo;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Date;

@ApplicationScoped
public class JWTUtils {

    public JWTUtils(JWTAuthContextInfo jwtAuthContextInfo) {
        jwtParser = new DefaultJWTParser(jwtAuthContextInfo);
    }

   JWTParser jwtParser;

    public static String gerarToken(Usuario usuarioDTO){
        Date data = new Date();
        long tempo = data.getTime();
        long exp = tempo + 1000L *60*60*24*30;
        return gerarToken(usuarioDTO, exp);
    }

    public static String gerarToken(Usuario usuario, long tempo){
        Date data = new Date();
        long exp = data.getTime() + tempo;
        return Jwt.issuer("https://medtec.com.br/issuer")
                .upn(usuario.getEmail())
                .groups(BooleanUtils.isTrue(usuario.getAdministrador()) ? "admin" : "user")
                .claim("nome", usuario.getNome())
                .claim("telefone", usuario.getTelefone())
                .claim("oidUsuario", usuario.getOid())
                .expiresAt(exp)
                .sign();
    }

    public String getClaim(String claim) {
        try {
            JsonWebToken jwt = jwtParser.parse(Sessao.getInstance().getToken());
            return jwt.getClaim(claim);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void verifyToken(String token) throws Exception {
        jwtParser.parse(token);
    }
}
