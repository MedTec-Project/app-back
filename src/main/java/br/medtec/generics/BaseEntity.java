package br.medtec.generics;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.UserSession;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@FilterDef(
        name = "user",
        parameters = {@ParamDef(
                name = "oidUserCreation",
                type = String.class
        )}
)
@Filters({@Filter(
        name = "user",
        condition = "oid_user_creation in (:oidUserCreation)"
)})
@Data
public class BaseEntity {

    @Id
    private String oid;

    @Column(name = "oid_user_creation", nullable = false, updatable = false)
    private String oidUserCreation;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "oid_user_update")
    private String oidUserUpdate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Version
    @JsonIgnore
    @JsonbTransient
    private Integer version;

    @PrePersist
    public void beforePersist() {

        if (this.oid == null) {
            this.oid = UUID.randomUUID().toString();
        }

        if (this.creationDate == null) {
            this.creationDate = new Date();
        }

        if (this.oidUserCreation == null) {
            this.oidUserCreation = this.getOidUser();
        }
    }

    @PreUpdate
    public void beforeUpdate() {
        this.updateDate = new Date();
        this.oidUserUpdate = this.getOidUser();
    }

    public void validateUser() {
        if ((!Objects.equals(UserSession.getUserType(), "admin")) &&
                ((this.oidUserCreation != null && !this.oidUserCreation.equals("user")) && (!Objects.equals(this.oidUserCreation, this.getOidUser())))) {
            throw new MEDBadRequestExecption("O Usúario Não tem permissão de acesso a esta entidade");
        }
    }

    private String getOidUser() {
        return UserSession.getOidUser();
    }
}
