package br.medtec.usuario;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String oid;
    private String email;
    private String senha;
    private String nome;
    private String telefone;
    private Boolean administrador;

    public Usuario toEntity(){
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setTelefone(this.telefone);
        usuario.setAdministrador(this.administrador);
        return usuario;
    }

}
