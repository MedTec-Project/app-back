package br.medtec.generics;
import br.medtec.utils.Sessao;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
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

    private String getOidUsuario() {
        return Sessao.getOidUsuario();
    }
}
