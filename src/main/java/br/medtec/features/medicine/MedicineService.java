package br.medtec.features.medicine;

import br.medtec.features.image.ImageService;
import br.medtec.utils.StringUtil;
import br.medtec.utils.Validations;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MedicineService {

    private final MedicineRepository medicineRepository;

    private final ImageService imageService;

    @Inject
    public MedicineService(MedicineRepository medicineRepository, ImageService imageService) {
        this.medicineRepository = medicineRepository;
        this.imageService = imageService;
    }

    @Transactional
    public Medicine registerMedicine(MedicineDTO medicineDTO) {
        validateMedicine(medicineDTO);

        Medicine medicine = medicineDTO.toEntity();

        medicine.setImagePath(imageService.saveImage(medicineDTO.getImageBase64(), medicineDTO.getName()));

        return medicineRepository.save(medicine);
    }

    @Transactional
    public Medicine updateMedicine(MedicineDTO medicineDTO, String oid) {
        validateMedicine(medicineDTO);

        Medicine medicine = medicineRepository.findByOid(oid);
        medicine.validateUser();
        Medicine updatedMedicine = medicineDTO.toEntity(medicine);

        return medicineRepository.update(updatedMedicine);
    }

    @Transactional
    public void deleteMedicine(String oid) {
        Medicine medicine = medicineRepository.findByOid(oid);
        medicine.validateUser();
        medicineRepository.delete(medicine);
    }

    @Transactional
    public void validateMedicine(MedicineDTO medicineDTO) {
        Validations validations = new Validations();

        if (!StringUtil.isValidString(medicineDTO.getName())) {
            validations.add("Nome do medicamento é obrigatório");
        }
        if (Medicine.MedicineCategory.valueOf(medicineDTO.getMedicineCategory()) == null) {
            validations.add("Categoria do medicamento é obrigatória");
        }

        if (Medicine.PharmaceuticalForm.valueOf(medicineDTO.getPharmaceuticalForm()) == null) {
            validations.add("Forma farmacêutica do medicamento é obrigatória");
        }

        if ((medicineDTO.getDosage() == null) || (medicineDTO.getDosage() == 0)) {
            validations.add("Dosagem do medicamento é obrigatória");
        }

        if (Medicine.DosageType.valueOf(medicineDTO.getDosageType()) == null) {
            validations.add("Tipo de dosagem do medicamento é obrigatório");
        }

        validations.throwErrors();
    }
}
