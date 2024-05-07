package br.medtec.utils;

import br.medtec.dto.UsuarioDTO;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Date;

public class JWTUtils {
    @Inject
    JWTParser jwtParser;

    public static String gerarToken(UsuarioDTO usuarioDTO){
        Date data = new Date();
        long tempo = data.getTime();
        long exp = tempo + 1000L *60*60*24*30;
        return gerarToken(usuarioDTO, exp);
    }

    public static String gerarToken(UsuarioDTO usuarioDTO, long tempo){
        Date data = new Date();
        long exp = data.getTime() + tempo;
        return Jwt.issuer("https://medtec.com.br/issuer")
                .upn(usuarioDTO.getEmail())
                .groups(BooleanUtils.isTrue(usuarioDTO.getAdministrador()) ? "admin" : "user")
                .claim("nome", usuarioDTO.getNome())
                .claim("telefone", usuarioDTO.getTelefone())
                .expiresAt(exp)
                .sign();
    }
}
