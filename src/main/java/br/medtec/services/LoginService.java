package br.medtec.services;

import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.repositories.LoginRepository;
import br.medtec.utils.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LoginService extends GenericsService<Usuario> {

    @Inject
    LoginRepository loginRepository;


    @Transactional
    public String login(UsuarioDTO usuarioDTO){
        if (!verificaExiste(usuarioDTO, true)){
            throw new MEDBadRequestExecption("Email ou Senha Incorreto");
        } else {
            return JWTUtils.gerarToken(usuarioDTO);
        }
    }

    @Transactional
    public Usuario criaUsuario(UsuarioDTO usuario){
        if (usuario != null) {
            validarUsuario(usuario);
            Usuario usuarioLogin = usuario.toEntity();
            persist(usuarioLogin);
            return usuarioLogin;
        }
        return null;
    }

    @Transactional
    public Boolean verificaExiste(UsuarioDTO usuarioDTO, Boolean verificarSenha){
        if (usuarioDTO != null) {
            Usuario usuarioLogin = loginRepository.findByEmailAndSenha(usuarioDTO.getEmail());
            usuarioDTO.setOid(usuarioLogin.getOid());
            return verificarSenha ? usuarioLogin.verificaSenha(usuarioDTO.getSenha()) : true;
        }
        return null;
    }

    @Transactional
    public void validarUsuario(UsuarioDTO usuarioDTO){
        Validcoes validcoes = new Validcoes();

        if (!UtilString.stringValida(usuarioDTO.getEmail()) || !UtilString.validarEmail(usuarioDTO.getEmail())) {
            validcoes.add("Email Invalido");
        }

        if (!UtilString.stringValida(usuarioDTO.getSenha())){
            validcoes.add("Senha Invalida");
        }

        if (!UtilString.stringValida(usuarioDTO.getNome())) {
            validcoes.add("Nome Invalido");
        }

        if ((!UtilString.stringValida(usuarioDTO.getTelefone())) || (!UtilString.validarTelefone(usuarioDTO.getTelefone()))){
            validcoes.add("Telefone Invalido");
        }

        validcoes.lancaErros();
    }
}
