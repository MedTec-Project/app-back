package br.medtec.medico;

import br.medtec.interfaces.GenericRepository;

public interface MedicoRepository extends GenericRepository<Medico> {
    public Boolean existsByCrm(String crm);

}
