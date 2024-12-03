package br.medtec.features.usuario;

import br.medtec.features.paciente.Paciente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "usuario")
public class Usuario extends Paciente {

    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "administrador")
    private Boolean administrador;

    public Boolean verificaSenha(String senha){
        return this.senha.equals(senha);
    }


    public UsuarioDTO toDTO(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(this.getNome());
        usuarioDTO.setEmail(this.email);
        usuarioDTO.setTelefone(this.getTelefone());
        usuarioDTO.setAdministrador(this.administrador);
        return usuarioDTO;
    }


}
