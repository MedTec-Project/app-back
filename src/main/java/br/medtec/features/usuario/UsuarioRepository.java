package br.medtec.features.usuario;

import br.medtec.generics.GenericRepository;

public interface UsuarioRepository extends GenericRepository<Usuario> {
    public Usuario findByEmail(String email);

}
