package br.medtec.unit;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.doctor.Doctor;
import br.medtec.features.doctor.DoctorDTO;
import br.medtec.features.doctor.DoctorRepository;
import br.medtec.features.doctor.DoctorService;
import br.medtec.utils.UserSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Doctor Tests")
@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @InjectMocks
    DoctorService doctorService;

    @Mock
    DoctorRepository doctorRepository;

    @BeforeAll
    void setup() { MockitoAnnotations.openMocks(this); }

    @Nested
    @Order(1)
    @DisplayName("Register Doctor")
    class RegisterDoctorTest {

        Doctor doctor;
        DoctorDTO doctorDTO;

        @BeforeEach
        void setup() {
            doctorDTO = new DoctorDTO();
            doctorDTO.setName("Richard");
            doctorDTO.setCrm("123");
            doctorDTO.setPhone("12345678911");
            doctor = doctorDTO.toEntity();
        }

        @Test
        @DisplayName("Successfully register doctor")
        void registerDoctorSuccessfully() {
            doctor = doctorService.createDoctor(doctorDTO);
            assertEquals(doctor, doctorDTO.toEntity());
            verify(doctorRepository, times(1)).save(doctor);
        }

        @Test
        @DisplayName("Register doctor with existing CRM")
        void registerDoctorWithExistingCrm() {
            when(doctorRepository.existsByCrm(anyString())).thenReturn(true);
            assertThrows(MEDBadRequestExecption.class, () -> doctorService.createDoctor(doctorDTO));
            verify(doctorRepository, times(0)).save(any());
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Update Doctor")
    class UpdateDoctorTest {
        Doctor doctor;
        DoctorDTO doctorDTO;

        @BeforeEach
        void setup() {
            doctorDTO = new DoctorDTO();
            doctorDTO.setOid("123");
            doctorDTO.setName("Richard");
            doctorDTO.setCrm("123");
            doctorDTO.setPhone("12345678911");
            doctor = doctorDTO.toEntity();
        }

        @Test
        @DisplayName("Successfully update doctor")
        void updateDoctorSuccessfully() {
            when(doctorRepository.findByOid(anyString())).thenReturn(doctor);
            when(doctorRepository.update(any())).thenReturn(doctor);
            doctor = doctorService.updateDoctor(doctorDTO, doctorDTO.getOid());
            assertEquals(doctor, doctorDTO.toEntity());
            verify(doctorRepository, times(1)).update(doctor);
        }

        @Test
        @DisplayName("Update doctor with nonexistent OID")
        void updateDoctorWithNonexistentOid() {
            when(doctorRepository.findByOid(anyString())).thenThrow(new MEDBadRequestExecption("Médico não encontrado"));
            assertThrows(MEDBadRequestExecption.class, () -> doctorService.updateDoctor(doctorDTO, doctorDTO.getOid()));
            verify(doctorRepository, times(0)).update(any());
        }
    }

    @Nested
    @Order(3)
    @DisplayName("Find Doctor")
    class FindDoctorTest {
        Doctor doctor;

        @BeforeEach
        void setup() {
            doctor = new Doctor();
            doctor.setOid("123");
            doctor.setName("Richard");
            doctor.setCrm("123");
            doctor.setPhone("12345678911");
            doctor.setOidUserCreation("123");
        }

        @Test
        @DisplayName("Successfully find doctor")
        void findDoctorSuccessfully() {
            when(doctorRepository.findByOid(anyString())).thenReturn(doctor);
            UserSession.getInstance().setOidUser("123");

            Doctor foundDoctor = doctorService.findDoctor("123");

            assertEquals(doctor, foundDoctor);
            verify(doctorRepository, times(1)).findByOid("123");
        }

        @Test
        @DisplayName("Find doctor with nonexistent OID")
        void findDoctorWithNonexistentOid() {
            when(doctorRepository.findByOid(anyString())).thenThrow(new MEDBadRequestExecption("Médico não encontrado"));

            assertThrows(MEDBadRequestExecption.class, () -> doctorService.findDoctor("123"));

            verify(doctorRepository, times(1)).findByOid("123");
        }

        @Test
        @DisplayName("Find doctor with OID different from logged-in user")
        void findDoctorWithDifferentOidFromLoggedUser() {
            when(doctorRepository.findByOid(anyString())).thenReturn(doctor);
            UserSession.getInstance().setOidUser("456");

            assertThrows(MEDBadRequestExecption.class, () -> doctorService.findDoctor("123"));

            verify(doctorRepository, times(1)).findByOid("123");
        }
    }
}
