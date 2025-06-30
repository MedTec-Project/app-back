package br.medtec.features.comorbidity;

import lombok.Data;

@Data
public class ComorbidityDTO {

    private String oid;
    private String name;
    private String description;
    private String oidComorbidityType;
    private String comorbidityTypeName;
    private String color;
    private String icon;
    private String image;

    public ComorbidityDTO() {

    }

    public ComorbidityDTO(String oid, String name, String description, String oidComorbidityType, String comorbidityTypeName, String color, String icon) {
        this.oid = oid;
        this.name = name;
        this.oidComorbidityType = oidComorbidityType;
        this.description = description;
        this.comorbidityTypeName = comorbidityTypeName;
        this.color = color;
        this.icon = icon;
    }

    public Comorbidity toEntity() {
        return toEntity(new Comorbidity());
    }

    public Comorbidity toEntity(Comorbidity comorbidity) {
        comorbidity.setName(this.name);
        comorbidity.setDescription(this.description);
        comorbidity.setOidComorbidityType(this.oidComorbidityType);
        return comorbidity;
    }

}
