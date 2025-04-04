package br.medtec.features.doctor;

import br.medtec.features.user.User;
import br.medtec.generics.Person;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "doctor")
@Data
@EqualsAndHashCode(callSuper = true)
public class Doctor extends Person {

    @Column(name = "crm")
    private String crm;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "doctor_user",
            joinColumns = @JoinColumn(name = "oid_doctor"),
            inverseJoinColumns = @JoinColumn(name = "oid_user"))
    private List<User> users;
}
