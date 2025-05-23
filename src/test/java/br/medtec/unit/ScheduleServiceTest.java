package br.medtec.unit;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.schedule.Schedule;
import br.medtec.features.schedule.ScheduleDTO;
import br.medtec.features.schedule.ScheduleRepository;
import br.medtec.features.schedule.ScheduleService;
import br.medtec.utils.UtilDate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduleService Tests")
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    // Helper para criar DTO válido
    private ScheduleDTO createValidDTO() {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setOidMedicine("med-123");
        dto.setOidDoctor("doc-456");
        dto.setInitialDate("2025-05-22");
        dto.setFinalDate("2025-05-30");
        dto.setQuantity(3);
        dto.setInterval(1);
        dto.setReminder("Tomar com água");
        return dto;
    }

    @Nested
    @DisplayName("Register Schedule")
    class RegisterTests {

        private ScheduleDTO validDTO;

        @BeforeEach
        void setUp() {
            validDTO = createValidDTO();
        }

        @Test
        @DisplayName("Should register schedule successfully")
        void shouldRegisterSuccessfully() {
            // Arrange
            Schedule expectedSchedule = validDTO.toEntity();
            when(scheduleRepository.save(any(Schedule.class))).thenReturn(expectedSchedule);

            // Act
            Schedule result = scheduleService.registerSchedule(validDTO);

            // Assert
            assertEquals("med-123", result.getOidMedicine());
            assertEquals(UtilDate.getDateByString("2025-05-22"), result.getInitialDate());
            verify(scheduleRepository, times(1)).save(any(Schedule.class));
        }

        @Test
        @DisplayName("Should fail when medicine is null")
        void shouldFailWhenMedicineIsNull() {
            validDTO.setOidMedicine(null);
            assertThrows(MEDBadRequestExecption.class,
                    () -> scheduleService.registerSchedule(validDTO));
        }

        @Test
        @DisplayName("Should fail when quantity is invalid")
        void shouldFailWhenQuantityInvalid() {
            validDTO.setQuantity(0);
            assertThrows(MEDBadRequestExecption.class,
                    () -> scheduleService.registerSchedule(validDTO));
        }

        @Test
        @DisplayName("Should fail when initial date is null")
        void shouldFailWhenInitialDateNull() {
            validDTO.setInitialDate(null);
            assertThrows(MEDBadRequestExecption.class,
                    () -> scheduleService.registerSchedule(validDTO));
        }
    }

    @Nested
    @DisplayName("Update Schedule")
    class UpdateTests {

        private ScheduleDTO validDTO;

        @BeforeEach
        void setUp() {
            validDTO = createValidDTO();
        }

        @Test
        @DisplayName("Should update schedule successfully")
        void shouldUpdateSuccessfully() {
            Schedule schedule = spy(validDTO.toEntity());
            schedule.setOid("ag-001");
            doNothing().when(schedule).validateUser();
            when(scheduleRepository.findByOid(anyString())).thenReturn(schedule);
            when(scheduleRepository.update(any(Schedule.class))).thenReturn(schedule);

            Schedule updated = scheduleService.updateSchedule(validDTO, "ag-001");

            assertNotNull(updated, "O retorno não deveria ser null");
            assertEquals("med-123", updated.getOidMedicine());
            assertEquals(UtilDate.getDateByString("2025-05-22"), updated.getInitialDate());
            verify(scheduleRepository, times(1)).update(any(Schedule.class));
        }

        @Test
        @DisplayName("Should fail when updating with invalid DTO")
        void shouldFailOnUpdateWithValidationError() {
            validDTO.setOidMedicine(null);
            assertThrows(MEDBadRequestExecption.class,
                    () -> scheduleService.updateSchedule(validDTO, "ag-001"));
        }
    }

    @Nested
    @DisplayName("Delete Schedule")
    class DeleteTests {

        private ScheduleDTO validDTO;

        @BeforeEach
        void setUp() {
            validDTO = createValidDTO();
        }

        @Test
        @DisplayName("Should delete schedule successfully")
        void shouldDeleteSuccessfully() {
            Schedule schedule = spy(validDTO.toEntity());
            schedule.setOid("ag-001");

            doNothing().when(schedule).validateUser();
            when(scheduleRepository.findByOid(anyString())).thenReturn(schedule);

            scheduleService.deleteSchedule("ag-001");

            verify(scheduleRepository, times(1)).delete(schedule);
        }
    }
}
