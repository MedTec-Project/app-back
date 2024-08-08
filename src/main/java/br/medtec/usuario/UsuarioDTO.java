package br.medtec.usuario;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class UsuarioDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "fernadesrichard312@gmail.com")
    private String email;

    @Schema(example = "123456")
    private String senha;

    @Schema(example = "Richard")
    private String nome;

    @Schema(example = "999999999")
    private String telefone;

    @Schema(hidden = true)
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
