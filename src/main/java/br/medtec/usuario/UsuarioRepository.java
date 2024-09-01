package br.medtec.usuario;

import br.medtec.generic.GenericRepository;

public interface UsuarioRepository extends GenericRepository<Usuario> {
    public Usuario findByEmail(String email);

}
