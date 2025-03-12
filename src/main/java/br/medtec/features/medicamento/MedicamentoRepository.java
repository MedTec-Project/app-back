package br.medtec.features.medicamento;

import br.medtec.features.symptom.Symptom;
import br.medtec.generics.GenericRepository;

import java.util.List;

public interface MedicamentoRepository extends GenericRepository<Medicamento> {

    public List<Medicamento> findAll(String nome, String oidFabricante, Integer categoriaMedicamento);
    public Symptom findSintomaByOid(String oid);

}
