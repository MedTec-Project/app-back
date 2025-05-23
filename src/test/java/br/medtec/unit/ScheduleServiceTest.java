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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("ScheduleService Tests")
public class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    private ScheduleDTO validDTO;

    @BeforeEach
    void init() {
        validDTO = new ScheduleDTO();
        validDTO.setOidMedicine("med-123");
        validDTO.setOidDoctor("doc-456");
        validDTO.setInitialDate("2025-05-22");
        validDTO.setFinalDate("2025-05-30");
        validDTO.setQuantity(3);
        validDTO.setInterval(1);
        validDTO.setReminder("Tomar com água");
    }

    @Test
    @Order(1)
    @DisplayName("Should register schedule successfully")
    void shouldRegisterSuccessfully() {
        Schedule result = scheduleService.registerSchedule(validDTO);

        assertEquals("med-123", result.getOidMedicine());
        assertEquals(UtilDate.getDateByString("2025-05-22"), result.getInitialDate());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
    }

    @Test
    @Order(2)
    @DisplayName("Should fail when medicine is null")
    void shouldFailWhenMedicineIsNull() {
        validDTO.setOidMedicine(null);
        assertThrows(MEDBadRequestExecption.class,
                () -> scheduleService.registerSchedule(validDTO));
    }

    @Test
    @Order(3)
    @DisplayName("Should fail when quantity is invalid")
    void shouldFailWhenQuantityInvalid() {
        validDTO.setQuantity(0);
        assertThrows(MEDBadRequestExecption.class,
                () -> scheduleService.registerSchedule(validDTO));
    }

    @Test
    @Order(4)
    @DisplayName("Should fail when initial date is null")
    void shouldFailWhenInitialDateNull() {
        validDTO.setInitialDate(null);
        assertThrows(MEDBadRequestExecption.class,
                () -> scheduleService.registerSchedule(validDTO));
    }

    @Test
    @Order(5)
    @DisplayName("Should update schedule successfully")
    void shouldUpdateSuccessfully() {
        // Arrange: spy para manter valores e permitir mock de validateUser()
        Schedule schedule = spy(validDTO.toEntity());
        schedule.setOid("ag-001");

        // Neutra validateUser()
        doNothing().when(schedule).validateUser();

        // **IMPORTANTE**: usar anyString() para cascar qualquer OID
        when(scheduleRepository.findByOid(anyString())).thenReturn(schedule);
        when(scheduleRepository.update(any(Schedule.class))).thenReturn(schedule);

        // Act
        Schedule updated = scheduleService.updateSchedule(validDTO, "ag-001");

        // Assert
        assertNotNull(updated, "O retorno não deveria ser null");
        assertEquals("med-123", updated.getOidMedicine());
        assertEquals(UtilDate.getDateByString("2025-05-22"), updated.getInitialDate());
        verify(scheduleRepository, times(1)).update(any(Schedule.class));
    }

    @Test
    @Order(6)
    @DisplayName("Should fail when updating invalid schedule")
    void shouldFailOnUpdateWithValidationError() {
        validDTO.setOidMedicine(null);
        assertThrows(MEDBadRequestExecption.class,
                () -> scheduleService.updateSchedule(validDTO, "ag-001"));
    }

    @Test
    @Order(7)
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
