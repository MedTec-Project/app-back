package br.medtec.features.doctor;

import br.medtec.generics.GenericRepository;

import java.util.List;

public interface DoctorRepository extends GenericRepository<Doctor> {
     Boolean existsByCrm(String crm);
     List<DoctorDTO> findAllDoctors();

}
