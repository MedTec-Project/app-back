package br.medtec.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
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
    @Column(name = "data_alteracao", nullable = true)
    private Date dataAlteracao;

    @PrePersist
    public void beforePersist() {

        if (oid == null) {
            oid = UUID.randomUUID().toString();
        }
    }
}
