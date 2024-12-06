package br.medtec.features.medico;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.Sessao;
import br.medtec.utils.UtilString;
import br.medtec.utils.Validcoes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class MedicoService {


    MedicoRepository medicoRepository;

    @Inject
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public Medico criarMedico(MedicoDTO medicoDTO) {
        validarMedico(medicoDTO);
        Medico medico = medicoDTO.toEntity();

        if (medicoRepository.existsByCrm(medico.getCrm())) {
            log.error("CRM já cadastrado {}", medico.getCrm());
            throw new MEDBadRequestExecption("CRM já cadastrado");
        }

        medicoRepository.save(medico);

        return medico;
    }

    @Transactional
    public Medico atualizarMedico(MedicoDTO medicoDTO, String oid) {
        validarMedico(medicoDTO);
        Medico medico = medicoRepository.findByOid(oid);
        medico.validarUsuario();
        Medico medicoAtualizado = medicoDTO.toEntity(medico);
        return medicoRepository.update(medicoAtualizado);
    }

    @Transactional
    public void deletarMedico(String oid) {
        Medico medico = medicoRepository.findByOid(oid);
        medico.validarUsuario();
        medicoRepository.delete(medico);
    }

    @Transactional
    public Medico buscarMedico(String oid) {
        Medico medico = medicoRepository.findByOid(oid);
        if (Sessao.getOidUsuario().equals(medico.getOidUsuarioCriacao())) {
            return medico;
        } else {
            log.error("Você {} não tem permissão para acessar este medico {}", Sessao.getOidUsuario(), oid);
            throw new MEDBadRequestExecption("Você não tem permissão para acessar este recurso");
        }
    }

    @Transactional
    public void validarMedico(MedicoDTO medicoDTO) {
        Validcoes validcoes = new Validcoes(this);
        if (medicoDTO == null) {
            throw new MEDBadRequestExecption("Médico Não Informado");
        }

        if (!UtilString.stringValida(medicoDTO.getNome())) {
            validcoes.add("Nome não pode ser nulo");
        }

        if (!UtilString.stringValida(medicoDTO.getCrm())) {
            validcoes.add("CRM não pode ser nulo");
        }

        if (!UtilString.validarTelefoneOrNull(medicoDTO.getTelefone())) {
            validcoes.add("Telefone invalido");
        }

        if (!UtilString.validarEmailOrNull(medicoDTO.getEmailContato())) {
            validcoes.add("Email invalido");
        }

        validcoes.lancaErros();
    }

}
