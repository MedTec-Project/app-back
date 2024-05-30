package br.medtec.entity;

import br.medtec.dto.UsuarioDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "usuario")
public class Usuario extends MedEntity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "telefone", unique = true)
    private String telefone;

    @Column(name = "administrador")
    private Boolean administrador;


    public Boolean verificaSenha(String senha){
        return this.senha.equals(senha);
    }


    public UsuarioDTO toDTO(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(this.nome);
        usuarioDTO.setEmail(this.email);
        usuarioDTO.setTelefone(this.telefone);
        usuarioDTO.setAdministrador(this.administrador);
        return usuarioDTO;
    }


}
