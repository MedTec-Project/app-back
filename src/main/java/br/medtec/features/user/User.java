package br.medtec.features.user;

import br.medtec.generics.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
public class User extends Person {

    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "admin")
    private Boolean admin;

    @Column(name = "image_path")
    private String imagePath;

    public Boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public UserDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setOid(this.getOid());
        userDTO.setName(this.getName());
        userDTO.setEmail(this.email);
        userDTO.setPhone(this.getPhone());
        userDTO.setAdmin(this.admin);
        userDTO.setImagePath(this.imagePath);
        return userDTO;
    }
}
