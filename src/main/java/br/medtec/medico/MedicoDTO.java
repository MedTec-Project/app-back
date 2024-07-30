package br.medtec.medico;

import br.medtec.entity.Paciente;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class MedicoDTO {
    private String oid;
    private String nome;
    private String emailContato;
    private String sobrenome;
    private String telefone;
    private String cpf;
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
