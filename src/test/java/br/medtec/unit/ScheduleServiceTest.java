package br.medtec.unit;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.schedule.Doctor;
import br.medtec.features.schedule.DoctorDTO;
import br.medtec.features.schedule.DoctorRepository;
import br.medtec.features.schedule.DoctorService;
import br.medtec.features.schedule.Schedule;
import br.medtec.features.schedule.ScheduleDTO;
import br.medtec.features.schedule.ScheduleRepository;
import br.medtec.features.schedule.ScheduleService;
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
@DisplayName("Schedule Tests")
@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @InjectMocks
    ScheduleService scheduleService;

    @Mock
    ScheduleRepository scheduleRepository;

    @BeforeAll
    void setup() { MockitoAnnotations.openMocks(this); }

    @Nested
    @Order(1)
    @DisplayName("Register Schedule")
    class RegisterScheduleTest {

        Schedule schedule;
        ScheduleDTO scheduleDTO;

        @BeforeEach
        void setup() {
            scheduleDTO = new ScheduleDTO();
            scheduleDTO.setName("Richard");
            scheduleDTO.setCrm("123");
            scheduleDTO.setPhone("12345678911");
            schedule = scheduleDTO.toEntity();
        }

        @Test
        @DisplayName("Successfully register schedule")
        void registerScheduleSuccessfully() {
            schedule = scheduleService.createSchedule(scheduleDTO);
            assertEquals(schedule, scheduleDTO.toEntity());
            verify(scheduleRepository, times(1)).save(schedule);
        }

        @Test
        @DisplayName("Register schedule with existing CRM")
        void registerScheduleWithExistingCrm() {
            when(scheduleRepository.existsByCrm(anyString())).thenReturn(true);
            assertThrows(MEDBadRequestExecption.class, () -> scheduleService.createSchedule(scheduleDTO));
            verify(scheduleRepository, times(0)).save(any());
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Update Schedule")
    class UpdateScheduleTest {
        Schedule schedule;
        ScheduleDTO scheduleDTO;

        @BeforeEach
        void setup() {
            scheduleDTO = new ScheduleDTO();
            scheduleDTO.setOid("123");
            scheduleDTO.setName("Richard");
            scheduleDTO.setCrm("123");
            scheduleDTO.setPhone("12345678911");
            schedule = scheduleDTO.toEntity();
        }

        @Test
        @DisplayName("Successfully update schedule")
        void updateScheduleSuccessfully() {
            when(scheduleRepository.findByOid(anyString())).thenReturn(schedule);
            when(scheduleRepository.update(any())).thenReturn(schedule);
            schedule = scheduleService.updateSchedule(scheduleDTO, scheduleDTO.getOid());
            assertEquals(schedule, scheduleDTO.toEntity());
            verify(scheduleRepository, times(1)).update(schedule);
        }

        @Test
        @DisplayName("Update schedule with nonexistent OID")
        void updateScheduleWithNonexistentOid() {
            when(scheduleRepository.findByOid(anyString())).thenThrow(new MEDBadRequestExecption("Médico não encontrado"));
            assertThrows(MEDBadRequestExecption.class, () -> scheduleService.updateSchedule(scheduleDTO, scheduleDTO.getOid()));
            verify(scheduleRepository, times(0)).update(any());
        }
    }

    @Nested
    @Order(3)
    @DisplayName("Find Schedule")
    class FindScheduleTest {
        Schedule schedule;

        @BeforeEach
        void setup() {
            schedule = new Schedule();
            schedule.setOid("123");
            schedule.setName("Richard");
            schedule.setCrm("123");
            schedule.setPhone("12345678911");
            schedule.setOidUserCreation("123");
        }

        @Test
        @DisplayName("Successfully find schedule")
        void findScheduleSuccessfully() {
            when(scheduleRepository.findByOid(anyString())).thenReturn(schedule);
            UserSession.getInstance().setOidUser("123");

            Schedule foundSchedule = scheduleService.findSchedule("123");

            assertEquals(schedule, foundSchedule);
            verify(scheduleRepository, times(1)).findByOid("123");
        }

        @Test
        @DisplayName("Find schedule with nonexistent OID")
        void findScheduleWithNonexistentOid() {
            when(scheduleRepository.findByOid(anyString())).thenThrow(new MEDBadRequestExecption("Médico não encontrado"));

            assertThrows(MEDBadRequestExecption.class, () -> scheduleService.findSchedule("123"));

            verify(scheduleRepository, times(1)).findByOid("123");
        }

        @Test
        @DisplayName("Find schedule with OID different from logged-in user")
        void findScheduleWithDifferentOidFromLoggedUser() {
            when(scheduleRepository.findByOid(anyString())).thenReturn(schedule);
            UserSession.getInstance().setOidUser("456");

            assertThrows(MEDBadRequestExecption.class, () -> scheduleService.findSchedule("123"));

            verify(scheduleRepository, times(1)).findByOid("123");
        }
    }
}
