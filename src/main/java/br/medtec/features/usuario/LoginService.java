package br.medtec.features.usuario;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LoginService {
    private UsuarioRepository usuarioRepository;

    @Inject
    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public String login(UsuarioDTO usuarioDTO){
        if (verificaExiste(usuarioDTO)){
            Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());
            return JWTUtils.gerarToken(usuario);
        } else {
            throw new MEDBadRequestExecption("Email ou Senha Incorreto");
        }
    }

    @Transactional
    public Usuario criaUsuario(UsuarioDTO usuario){
        if (usuario != null) {
            validarUsuario(usuario);
            Usuario usuarioLogin = usuario.toEntity();
            usuarioRepository.save(usuarioLogin);
            return usuarioLogin;
        }
        return null;
    }

    @Transactional
    public Boolean verificaExiste(UsuarioDTO usuarioDTO){
        if (usuarioDTO != null) {
            Usuario usuarioLogin = usuarioRepository.findByEmail(usuarioDTO.getEmail());
            return usuarioLogin != null;
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
