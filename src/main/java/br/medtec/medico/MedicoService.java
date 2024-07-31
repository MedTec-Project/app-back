package br.medtec.medico;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.Sessao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MedicoService {


    MedicoRepository medicoRepository;

    @Inject
    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Transactional
    public Medico criarMedico(MedicoDTO medicoDTO) {
//        validarMedicamento(medicoDTO);

        Medico medico = medicoDTO.toEntity();

        if (medicoRepository.existsByCrm(medico.getCrm())) {
            throw new MEDBadRequestExecption("CRM já cadastrado");
        }

        medicoRepository.save(medico);

        return medico;
    }

    @Transactional
    public Medico atualizarMedico(MedicoDTO medicoDTO) {
//        validarMedicamento(medicoDTO);
        Medico medico = medicoRepository.findByOid(medicoDTO.getOid());
        Medico medicoAtualizado = medicoDTO.toEntity(medico);
        return medicoRepository.update(medicoAtualizado);
    }

    @Transactional
    public Medico buscarMedico(String oid) {
        Medico medico = medicoRepository.findByOid(oid);


        if (Sessao.getOidUsuario().equals(medico.getOidUsuarioCriacao())) {
            return medico;
        } else {
            throw new MEDBadRequestExecption("Você não tem permissão para acessar este recurso");
        }
    }

}
