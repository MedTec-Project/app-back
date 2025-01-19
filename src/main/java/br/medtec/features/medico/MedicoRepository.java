package br.medtec.features.medico;

import br.medtec.generics.GenericRepository;

public interface MedicoRepository extends GenericRepository<Medico> {
     Boolean existsByCrm(String crm);

}
