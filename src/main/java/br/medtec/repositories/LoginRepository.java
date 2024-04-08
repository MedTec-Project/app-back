package br.medtec.repositories;

import br.medtec.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class LoginRepository{

    @Inject
    EntityManager em;

    public Usuario autenticar(String email, String Senha){
        Usuario usuario = em.find(Usuario.class, email);
        if(usuario.getSenha().equals(Senha)){
            return usuario;
        }
        return null;
    }

}
