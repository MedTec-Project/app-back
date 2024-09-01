<<<<<<<< HEAD:src/main/java/br/medtec/generics/MedEntity.java
package br.medtec.generics;
import br.medtec.exceptions.MEDBadRequestExecption;
========
package br.medtec.generic;
import br.medtec.exceptions.MEDExecption;
>>>>>>>> 2cb6c86 (refactor(estrutura): refatorado as estrutura das pasta para minimizar a quantidade de pastas.):src/main/java/br/medtec/generic/MedEntity.java
import br.medtec.utils.Sessao;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Data
public class MedEntity {

    @Id
    private String oid;

    @Column (name = "oid_usuario_criacao", nullable = false, updatable = false)
    private String oidUsuarioCriacao;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "oid_usuario_alteracao")
    private String oidUsuarioAlteracao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_alteracao")
    private Date dataAlteracao;

    @Version
    private Integer versao;

    @PrePersist
    public void beforePersist() {

        if (this.oid == null) {
            this.oid = UUID.randomUUID().toString();
        }

        if (this.dataCriacao == null) {
            this.dataCriacao = new Date();
        }

        if (this.oidUsuarioCriacao == null) {
            this.oidUsuarioCriacao = this.getOidUsuario();
        }

    }

    @PreUpdate
    public void beforeUpdate(){
        this.dataAlteracao = new Date();
        this.oidUsuarioAlteracao = this.getOidUsuario();
    }

    public void validarUsuario() {
        if (this.oidUsuarioCriacao != null && this.oidUsuarioCriacao.equals("user") || !Objects.equals(this.oidUsuarioCriacao, this.getOidUsuario())) {
            throw new MEDBadRequestExecption("Usuario não tem permissão para alterar");
        }

    }

    private String getOidUsuario() {
        return Sessao.getOidUsuario();
    }
}
