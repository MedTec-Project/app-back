package br.medtec.services;

import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.repositories.LoginRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LoginService {

    @Inject
    LoginRepository loginRepository;

    @Inject
    EntityManager em;

    @Transactional
    public Boolean verificaExiste(UsuarioDTO usuario){
        if (usuario != null) {
            Usuario usuarioLogin = loginRepository.autenticar(usuario.getEmail(), usuario.getSenha());
            return usuarioLogin != null;
        }
        return null;
    }

    @Transactional
    public Usuario criaUsuario(UsuarioDTO usuario){
        if (usuario != null) {
            if (!verificaExiste(usuario)) {
                Usuario usuarioLogin = new Usuario();
                usuarioLogin.setEmail(usuario.getEmail());
                usuarioLogin.setSenha(usuario.getSenha());
                usuarioLogin.setNome(usuario.getNome());
                usuarioLogin.setTelefone(usuario.getTelefone());
                em.persist(usuarioLogin);
                return usuarioLogin;
            }
            else {
               return null;
            }
        }
        return null;
    }
}
