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

    public Boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public UserDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(this.getName());
        userDTO.setEmail(this.email);
        userDTO.setPhone(this.getPhone());
        userDTO.setAdmin(this.admin);
        return userDTO;
    }
}
