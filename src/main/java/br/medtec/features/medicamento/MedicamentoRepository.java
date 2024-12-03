package br.medtec.features.medicamento;

import br.medtec.generics.GenericRepository;

import java.util.List;

public interface MedicamentoRepository extends GenericRepository<Medicamento> {

    public List<Medicamento> findAll(String nome, String oidFabricante, Integer categoriaMedicamento);
    public Sintoma findSintomaByOid(String oid);

}
