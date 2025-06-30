package br.medtec.features.comorbidity;

import br.medtec.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comorbidity")
@Data
public class Comorbidity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "oid_comorbidity_type")
    private String oidComorbidityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oid_comorbidity_type", insertable = false, updatable = false)
    private ComorbidityType comorbidityType;

    public ComorbidityDTO toDTO() {
        ComorbidityDTO comorbidityDTO = new ComorbidityDTO();
        comorbidityDTO.setOid(this.getOid());
        comorbidityDTO.setName(this.name);
        comorbidityDTO.setDescription(this.description);
        comorbidityDTO.setOidComorbidityType(this.oidComorbidityType);
        if (this.comorbidityType != null) {
            comorbidityDTO.setComorbidityTypeName(this.comorbidityType.getName());
            comorbidityDTO.setColor(this.comorbidityType.getColor());
            comorbidityDTO.setIcon(this.comorbidityType.getIcon());
        }
        return comorbidityDTO;
    }

}
