package br.medtec.usuario;

import br.medtec.interfaces.GenericRepository;

public interface UsuarioRepository extends GenericRepository<Usuario> {
    public Usuario findByEmail(String email);

}
