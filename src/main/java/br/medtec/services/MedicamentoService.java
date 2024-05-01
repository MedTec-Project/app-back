package br.medtec.services;

import br.medtec.dto.MedicamentoDTO;
import br.medtec.entity.Medicamento;
import br.medtec.entity.Sintoma;
import br.medtec.entity.Usuario;
import br.medtec.repositories.MedicamentoRepository;
import br.medtec.utils.GenericsService;
import br.medtec.utils.UtilColecao;
import br.medtec.utils.UtilString;
import br.medtec.utils.Validcoes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class MedicamentoService extends GenericsService<Medicamento> {

    @Inject
    MedicamentoRepository medicamentoRepository;

    public Medicamento cadastrarMedicamento(MedicamentoDTO medicamentoDTO) {
        validarMedicamento(medicamentoDTO);

        Medicamento medicamento = montarMedicamento(medicamentoDTO);

        persist(medicamento);
        return medicamento;
    }

    public Medicamento atualizarMedicamento(MedicamentoDTO medicamentoDTO) {
        validarMedicamento(medicamentoDTO);

        Medicamento medicamento = montarMedicamento(medicamentoDTO);

        return (Medicamento) merge(medicamento);
    }

    public void deletarMedicamento(String oid) {
        Medicamento medicamento = medicamentoRepository.findByOid(oid);
        if (medicamento != null) {
            remove(medicamento);
        }
    }

    public Medicamento buscarMedicamento(String oid) {
        return medicamentoRepository.findByOid(oid);
    }

    public List<Medicamento> buscarMedicamentos() {
      if (UtilColecao.colecaoValida(medicamentoRepository.findAll())) {
          return medicamentoRepository.findAll();
      } else {
            return null;
      }
    }

    public Medicamento montarMedicamento(MedicamentoDTO medicamentoDTO){
        Medicamento medicamento;
        if (UtilString.stringValida(medicamentoDTO.getOid())) {
            medicamento = medicamentoRepository.findByOid(medicamentoDTO.getOid());
        } else {
            medicamento = new Medicamento();
        }
        medicamento.setNome(medicamentoDTO.getNome());
        medicamento.setCategoriaMedicamento(Medicamento.CategoriaMedicamento.valueOf(medicamentoDTO.getCategoriaMedicamento()));
        medicamento.setFormaFarmaceutica(Medicamento.FormaFarmaceutica.valueOf(medicamentoDTO.getFormaFarmaceutica()));
        medicamento.setOidFabricante(medicamentoDTO.getOidFabricante());
        medicamento.setDosagem(medicamentoDTO.getDosagem());

        if (UtilColecao.colecaoValida(medicamentoDTO.getSintomas())) {
            medicamentoDTO.getSintomas().forEach(sintoma -> {
                Sintoma sintomaMedicamento;
                if (sintoma.getOid() != null) {
                    sintomaMedicamento = medicamentoRepository.findSintomaByOid(sintoma.getOid());
                } else {
                    sintomaMedicamento = new Sintoma();
                }
                sintomaMedicamento.setNome(sintoma.getNome());

                medicamento.getSintomas().add(sintomaMedicamento);
            });

            medicamento.getSintomas().removeIf(sintoma -> medicamentoDTO.getSintomas().stream().noneMatch(sintomaDTO -> sintomaDTO.getOid().equals(sintoma.getOid())));
        } else {
            medicamento.setSintomas(null);
        }

        if (UtilColecao.colecaoValida(medicamentoDTO.getEfeitosColaterais())) {
            medicamentoDTO.getEfeitosColaterais().forEach(efeitoColateral -> {
                Sintoma efeitoColateralMedicamento;
                if (efeitoColateral.getOid() != null) {
                    efeitoColateralMedicamento = medicamentoRepository.findSintomaByOid(efeitoColateral.getOid());
                } else {
                    efeitoColateralMedicamento = new Sintoma();
                }
                efeitoColateralMedicamento.setNome(efeitoColateral.getNome());

                medicamento.getEfeitosColaterais().add(efeitoColateralMedicamento);
            });

            medicamento.getEfeitosColaterais().removeIf(efeitoColateral -> medicamentoDTO.getEfeitosColaterais().stream().noneMatch(efeitoColateralDTO -> efeitoColateralDTO.getOid().equals(efeitoColateral.getOid())));
        } else {
            medicamento.setEfeitosColaterais(null);
        }

        return medicamento;
    }

    public void validarMedicamento(MedicamentoDTO medicamentoDTO) {
        Validcoes validacoes = new Validcoes();
        if (UtilString.stringValida(medicamentoDTO.getNome())) {
           validacoes.add("Nome do medicamento é obrigatório");
        }
        if (Medicamento.CategoriaMedicamento.valueOf(medicamentoDTO.getCategoriaMedicamento()) == null) {
            validacoes.add("Categoria do medicamento é obrigatório");
        }

        if (Medicamento.FormaFarmaceutica.valueOf(medicamentoDTO.getFormaFarmaceutica()) == null) {
            validacoes.add("Forma farmaceutica do medicamento é obrigatório");
        }

        if (UtilString.stringValida(medicamentoDTO.getOidFabricante())) {
            validacoes.add("Fabricante do medicamento é obrigatório");
        }

        if (UtilString.stringValida(medicamentoDTO.getDosagem())) {
            validacoes.add("Fabricante do medicamento é obrigatório");
        }

        validacoes.lancaErros();
    }

}
