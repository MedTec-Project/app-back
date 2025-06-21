package br.medtec.features.doctor;

import br.medtec.generics.GenericRepository;

public interface DoctorRepository extends GenericRepository<Doctor> {
     Boolean existsByCrm(String crm);

}
