package br.medtec.services;

import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.repositories.LoginRepository;
import br.medtec.utils.GenericsService;
import br.medtec.utils.UtilString;
import br.medtec.utils.Validcoes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LoginService extends GenericsService<Usuario> {

    @Inject
    LoginRepository loginRepository;

    @Transactional
    public Boolean verificaExiste(UsuarioDTO usuarioDTO, Boolean verificarSenha){
        if (usuarioDTO != null) {
            Usuario usuarioLogin = loginRepository.findByEmailAndSenha(usuarioDTO.getEmail());
            return verificarSenha ? usuarioLogin.getSenha().equals(usuarioDTO.getSenha()) : usuarioLogin != null;
        }
        return null;
    }

    @Transactional
    public Usuario criaUsuario(UsuarioDTO usuario){
        if (usuario != null) {
            validarUsuario(usuario);
            Usuario usuarioLogin = new Usuario();
            usuarioLogin.setEmail(usuario.getEmail());
            usuarioLogin.setSenha(usuario.getSenha());
            usuarioLogin.setNome(usuario.getNome());
            usuarioLogin.setTelefone(usuario.getTelefone());
            persist(usuarioLogin);
            return usuarioLogin;
        }
        return null;
    }

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
