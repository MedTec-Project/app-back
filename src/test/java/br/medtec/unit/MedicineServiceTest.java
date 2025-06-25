package br.medtec.unit;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.image.ImageService;
import br.medtec.features.medicine.MedicineDTO;
import br.medtec.features.medicine.Medicine;
import br.medtec.features.medicine.MedicineRepository;
import br.medtec.features.medicine.MedicineService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Medicine Tests")
@ExtendWith(MockitoExtension.class)
public class MedicineServiceTest {

    @InjectMocks
    MedicineService medicineService;

    @Mock
    MedicineRepository medicineRepository;

    @Mock
    ImageService imageService;

    @Nested
    @Order(1)
    @DisplayName("Register Medicine")
    class RegisterMedicineTest {
        Medicine medicine;
        MedicineDTO medicineDTO;

        @BeforeEach
        void setup() {
            medicineDTO = new MedicineDTO();
            medicineDTO.setName("Dorflex");
            medicineDTO.setMedicineCategory(Medicine.MedicineCategory.ANTIALERGIC.name());
            medicineDTO.setPharmaceuticalForm(Medicine.PharmaceuticalForm.CAPSULE.name());
            medicineDTO.setDosage(1.0);
            medicineDTO.setDosageType(1);
            medicineDTO.setOidManufacturer("123");
            medicineDTO.setImageBase64("imageBase64");
            medicine = medicineDTO.toEntity();
        }

        @Test
        @DisplayName("Successfully register medicine")
        void registerMedicineSuccessfully() {
            when(imageService.saveImage(anyString(), anyString())).thenReturn("imageBase64");
            when(medicineRepository.save(any(Medicine.class))).thenReturn(medicine);

            medicine = medicineService.registerMedicine(medicineDTO);

            assertNotNull(medicine);
            assertEquals(medicineDTO.toEntity(), medicine);
        }

        @Test
        @DisplayName("Register medicine with empty name")
        void registerMedicineWithEmptyName() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setName("");
                medicineService.registerMedicine(medicineDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setName(null);
                medicineService.registerMedicine(medicineDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setName(" ");
                medicineService.registerMedicine(medicineDTO);
            });
        }

        @Test
        @DisplayName("Register medicine with empty category")
        void registerMedicineWithEmptyCategory() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setMedicineCategory(null);
                medicineService.registerMedicine(medicineDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setMedicineCategory(null);
                medicineService.registerMedicine(medicineDTO);
            });
        }

        @Test
        @DisplayName("Register medicine with empty pharmaceutical form")
        void registerMedicineWithEmptyPharmaceuticalForm() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setPharmaceuticalForm(null);
                medicineService.registerMedicine(medicineDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setPharmaceuticalForm(null);
                medicineService.registerMedicine(medicineDTO);
            });
        }

        @Test
        @DisplayName("Register medicine with empty dosage")
        void registerMedicineWithEmptyDosage() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setDosage(null);
                medicineService.registerMedicine(medicineDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicineDTO.setDosage(0.0);
                medicineService.registerMedicine(medicineDTO);
            });
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Edit Medicine")
    class EditMedicineTest {
        Medicine medicine;
        MedicineDTO medicineDTO;

        @BeforeEach
        void setup() {
            medicineDTO = new MedicineDTO();
            medicineDTO.setName("Dorflex");
            medicineDTO.setMedicineCategory(Medicine.MedicineCategory.ANTIALERGIC.name());
            medicineDTO.setPharmaceuticalForm(Medicine.PharmaceuticalForm.CAPSULE.name());
            medicineDTO.setDosage(1.0);
            medicineDTO.setDosageType(1);
            medicineDTO.setOidManufacturer("123");

            medicine = medicineDTO.toEntity();
        }

        @Test
        @DisplayName("Successfully edit medicine")
        void editMedicineSuccessfully() {
            medicineDTO.setName("Dorflex 2");
            medicineDTO.setMedicineCategory(Medicine.MedicineCategory.ANTIALERGIC.name());
            medicineDTO.setPharmaceuticalForm(Medicine.PharmaceuticalForm.CAPSULE.name());
            medicineDTO.setDosage(2.0);
            medicineDTO.setDosageType(2);
            medicineDTO.setOidManufacturer("1234");
            medicine = spy(medicine);
            when(medicineRepository.findByOid(medicineDTO.getOid())).thenReturn(medicine);
            when(medicineRepository.update(medicine)).thenReturn(medicine);
            doNothing().when(medicine).validateUser();

            medicine = medicineService.updateMedicine(medicineDTO, medicineDTO.getOid());

            assertNotNull(medicine);
            assertEquals(medicineDTO.toEntity(), medicine);
        }
    }

    @Nested
    @Order(3)
    @DisplayName("Delete Medicine")
    class DeleteMedicineTest {
        Medicine medicine;
        MedicineDTO medicineDTO;

        @BeforeEach
        void setup() {
            medicineDTO = new MedicineDTO();
            medicineDTO.setOid("123");
            medicineDTO.setName("Dorflex");
            medicineDTO.setMedicineCategory(Medicine.MedicineCategory.ANTIALERGIC.name());
            medicineDTO.setPharmaceuticalForm(Medicine.PharmaceuticalForm.CAPSULE.name());
            medicineDTO.setDosage(1.0);
            medicineDTO.setDosageType(1);
            medicineDTO.setOidManufacturer("123");

            medicine = medicineDTO.toEntity();
        }

        @Test
        @DisplayName("Successfully delete medicine")
        void deleteMedicineSuccessfully() {
            medicine = spy(medicine);
           when(medicineRepository.findByOid(medicineDTO.getOid())).thenReturn(medicine);
           doNothing().when(medicine).validateUser();

           medicineService.deleteMedicine(medicineDTO.getOid());

           verify(medicineRepository, times(1)).delete(medicine);
        }
    }

    @Nested
    @Order(4)
    @DisplayName("Find Medicines")
    class FindMedicineTest {
        Medicine medicine;
        MedicineDTO medicineDTO;

        @BeforeEach
        void setup() {
            medicineDTO = new MedicineDTO();
            medicineDTO.setOid("123");
            medicineDTO.setName("Dorflex");
            medicineDTO.setMedicineCategory(Medicine.MedicineCategory.ANTIALERGIC.name());
            medicineDTO.setPharmaceuticalForm(Medicine.PharmaceuticalForm.CAPSULE.name());
            medicineDTO.setDosage(1.0);
            medicineDTO.setDosageType(1);
            medicineDTO.setOidManufacturer("123");

            medicine = medicineDTO.toEntity();
        }
    }
}
