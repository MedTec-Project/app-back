package br.medtec.medicamento;

import br.medtec.generic.GenericRepository;

import java.util.List;

public interface MedicamentoRepository extends GenericRepository<Medicamento> {

    public List<Medicamento> findAll(String nome, String oidFabricante, Integer categoriaMedicamento);
    public Sintoma findSintomaByOid(String oid);

}
