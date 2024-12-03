package br.medtec.features.usuario;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class UsuarioLoginDTO extends UsuarioDTO {

    @Schema(hidden = true)
    public String nome;

    @Schema(hidden = true)
    public String telefone;

}
