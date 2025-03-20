package br.medtec.features.medicine;

import br.medtec.features.symptom.Symptom;
import br.medtec.generics.GenericRepository;

import java.util.List;

public interface MedicineRepository extends GenericRepository<Medicine> {

    List<Medicine> findAll(String name, String oidManufacturer, Integer medicineCategory);
    Symptom findSymptomByOid(String oid);
}
