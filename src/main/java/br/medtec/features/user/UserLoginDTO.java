package br.medtec.features.user;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class UserLoginDTO extends UserDTO {

    @Schema(hidden = true)
    public String name;

    @Schema(hidden = true)
    public String phone;

}
