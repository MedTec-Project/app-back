package br.medtec.features.comorbidity;

import br.medtec.generics.GenericRepository;

import java.util.List;

public interface ComorbidityRepository extends GenericRepository<Comorbidity> {

    List<ComorbidityDTO> findAllByUser();
    List<ComorbidityType> findAllTypes();
}
