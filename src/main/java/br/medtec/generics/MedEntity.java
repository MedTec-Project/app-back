package br.medtec.generics;
import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.Sessao;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@FilterDef(
        name = "usuario",
        parameters = {@ParamDef(
                name = "oidusuariocriacao",
                type = String.class
        )}
)
@Filters({@Filter(
        name = "usuario",
        condition = "oid_usuario_criacao in (:oidusuariocriacao)"
)})
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
        if ((!Objects.equals(Sessao.getTipoUsuario(), "admin")) &&
                ((this.oidUsuarioCriacao != null && this.oidUsuarioCriacao.equals("user")) || (!Objects.equals(this.oidUsuarioCriacao, this.getOidUsuario())))) {
            throw new MEDBadRequestExecption("Usuario não tem permissão para esse recurso");
        }

    }

    private String getOidUsuario() {
        return Sessao.getOidUsuario();
    }
}
