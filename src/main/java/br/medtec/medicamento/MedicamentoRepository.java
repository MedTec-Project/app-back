package br.medtec.medicamento;

import br.medtec.entity.Sintoma;
import br.medtec.interfaces.GenericRepository;

import java.util.List;

public interface MedicamentoRepository extends GenericRepository<Medicamento> {

    public List<Medicamento> findAll(String nome, String oidFabricante, Integer categoriaMedicamento);
    public Sintoma findSintomaByOid(String oid);

}
