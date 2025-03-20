package br.medtec.features.user;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class UserDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "fernadesrichard312@gmail.com")
    private String email;

    @Schema(example = "123456")
    private String password;

    @Schema(example = "Richard")
    private String name;

    @Schema(example = "999999999")
    private String phone;

    @Schema(hidden = true)
    private Boolean admin;

    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setPhone(this.phone);
        user.setAdmin(this.admin);
        return user;
    }
}
