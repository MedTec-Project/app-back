package br.medtec.medicamento;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.UtilString;
import br.medtec.utils.Validcoes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class MedicamentoService {


    public MedicamentoRepository medicamentoRepository;

    @Inject
    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Transactional
    public Medicamento cadastrarMedicamento(MedicamentoDTO medicamentoDTO) {
        validarMedicamento(medicamentoDTO);

        Medicamento medicamento = medicamentoDTO.toEntity();

        medicamentoRepository.save(medicamento);

        return medicamento;
    }

    @Transactional
    public Medicamento atualizarMedicamento(MedicamentoDTO medicamentoDTO) {
        validarMedicamento(medicamentoDTO);

        Optional<Medicamento> medicamento = medicamentoRepository.findByOid(medicamentoDTO.getOid());

        if (medicamento.isEmpty()) {
            throw new MEDBadRequestExecption("Medicamento não encontrado");
        }

        Medicamento medicamentoAtualizado = medicamentoDTO.toEntity(medicamento.get());

        return medicamentoRepository.update(medicamentoAtualizado);
    }

    @Transactional
    public void deletarMedicamento(String oid) {
        medicamentoRepository.findByOid(oid).ifPresent(medicamentoRepository::delete);
    }

    @Transactional
    public void validarMedicamento(MedicamentoDTO medicamentoDTO) {
        Validcoes validacoes = new Validcoes();
        if (!UtilString.stringValida(medicamentoDTO.getNome())) {
           validacoes.add("Nome do medicamento é obrigatório");
        }
        if (Medicamento.CategoriaMedicamento.valueOf(medicamentoDTO.getCategoriaMedicamento()) == null) {
            validacoes.add("Categoria do medicamento é obrigatório");
        }

        if (Medicamento.FormaFarmaceutica.valueOf(medicamentoDTO.getFormaFarmaceutica()) == null) {
            validacoes.add("Forma farmaceutica do medicamento é obrigatório");
        }

        if ((medicamentoDTO.getDosagem() == null) || (medicamentoDTO.getDosagem() == 0)) {
            validacoes.add("Dosagem do medicamento é obrigatório");
        }

        if (Medicamento.TipoDosagem.valueOf(medicamentoDTO.getTipoDosagem()) == null) {
            validacoes.add("Tipo de dosagem do medicamento é obrigatório");
        }

        validacoes.lancaErros();
    }

}
