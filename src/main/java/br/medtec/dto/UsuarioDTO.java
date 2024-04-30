package br.medtec.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String oid;
    private String email;
    private String senha;
    private String nome;
    private String telefone;
    private Boolean administrador;
}
