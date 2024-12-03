package br.medtec.features.medico;

import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class MedicoDTO {

    @Schema(hidden = true)
    private String oid;

    @Schema(example = "Richard")
    private String nome;

    @Schema(example = "fernandesrichard312@gmail.com")
    private String emailContato;

    @Schema(example = "Fernandes")
    private String sobrenome;

    @Schema(example = "999999999")
    private String telefone;

    @Schema(example = "12345678901")
    private String cpf;

    @Schema(example = "123456")
    private String crm;

    public Medico toEntity() {
        Medico medico = new Medico();
        toEntity(medico);
        return medico;
    }

    public Medico toEntity(Medico medico) {
        medico.setNome(this.nome);
        medico.setEmailContato(this.emailContato);
        medico.setSobrenome(this.sobrenome);
        medico.setTelefone(this.telefone);
        medico.setCpf(this.cpf);
        medico.setCrm(this.crm);
        return medico;
    }
}
