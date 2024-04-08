package br.medtec.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String email;
    private String senha;
    private String nome;
    private String telefone;
}
