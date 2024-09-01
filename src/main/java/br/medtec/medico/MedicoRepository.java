package br.medtec.medico;

import br.medtec.generic.GenericRepository;

public interface MedicoRepository extends GenericRepository<Medico> {
     Boolean existsByCrm(String crm);

}
