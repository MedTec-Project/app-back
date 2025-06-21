package br.medtec.features.doctor;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class DoctorDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "Richard")
    private String name;

    @Schema(example = "fernandesrichard312@gmail.com")
    private String contactEmail;

    @Schema(example = "Fernandes")
    private String lastName;

    @Schema(example = "999999999")
    private String phone;

    @Schema(example = "12345678901")
    private String cpf;

    @Schema(example = "123456")
    private String crm;

    public Doctor toEntity() {
        Doctor doctor = new Doctor();
        toEntity(doctor);
        return doctor;
    }

    public Doctor toEntity(Doctor doctor) {
        doctor.setName(this.name);
        doctor.setContactEmail(this.contactEmail);
        doctor.setLastName(this.lastName);
        doctor.setPhone(this.phone);
        doctor.setCpf(this.cpf);
        doctor.setCrm(this.crm);
        return doctor;
    }
}
