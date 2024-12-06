package br.medtec.unit;

import br.medtec.features.medicamento.MedicamentoDTO;
import br.medtec.features.medicamento.Medicamento;
import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.medicamento.MedicamentoRepository;
import br.medtec.features.medicamento.MedicamentoService;
import br.medtec.utils.Sessao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Teste Medicamento")
@ExtendWith(MockitoExtension.class)
public class MedicamentoServiceTest {

    @InjectMocks
    MedicamentoService medicamentoService;

    @Mock
    MedicamentoRepository medicamentoRepository;


    @BeforeAll
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @Order(1)
    @DisplayName("Cadastro de medicamento")
    class CadastroMedicamentoTest {
        Medicamento medicamento;

        MedicamentoDTO medicamentoDTO;

        @BeforeEach
        void setup() {
            medicamentoDTO = new MedicamentoDTO();
            medicamentoDTO.setNome("Dorflex");
            medicamentoDTO.setCategoriaMedicamento(1);
            medicamentoDTO.setFormaFarmaceutica(1);
            medicamentoDTO.setDosagem(1.0);
            medicamentoDTO.setTipoDosagem(1);
            medicamentoDTO.setOidFabricante("123");
            medicamento = medicamentoDTO.toEntity();
        }

        @Test
        @DisplayName("Cadastrar medicamento com sucesso")
        void cadastrarMedicamentoComSucesso() {
            medicamento = medicamentoService.cadastrarMedicamento(medicamentoDTO);
            assertNotNull(medicamento);
            assertEquals(medicamentoDTO.toEntity(), medicamento);
        }

        @Test
        @DisplayName("Cadastrar medicamento com nome vazio")
        void cadastrarMedicamentoComNomeVazio() {

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setNome("");
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setNome(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setNome(" ");
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Cadastrar medicamento com categoria vazia")
        void cadastrarMedicamentoComCategoriaVazia() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setCategoriaMedicamento(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setCategoriaMedicamento(999);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Cadastrar medicamento com forma farmaceutica vazia")
        void cadastrarMedicamentoComFormaFarmaceuticaVazia() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setFormaFarmaceutica(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setFormaFarmaceutica(999);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Cadastrar medicamento com dosagem vazia")
        void cadastrarMedicamentoComDosagemVazia() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setDosagem(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                medicamentoDTO.setDosagem(0.0);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

    }

    @Nested
    @Order(2)
    @DisplayName("Editar medicamento")
    class EditarMedicamentoTest {
        Medicamento medicamento;

        MedicamentoDTO medicamentoDTO;

        @BeforeEach
        void setup() {
            medicamentoDTO = new MedicamentoDTO();
            medicamentoDTO.setNome("Dorflex");
            medicamentoDTO.setCategoriaMedicamento(1);
            medicamentoDTO.setFormaFarmaceutica(1);
            medicamentoDTO.setDosagem(1.0);
            medicamentoDTO.setTipoDosagem(1);
            medicamentoDTO.setOidFabricante("123");

            medicamento = medicamentoDTO.toEntity();
            medicamento.setOidUsuarioCriacao("teste");
            Sessao.getInstance().setOidUsuario("teste");
        }

        @Test
        @DisplayName("Editar medicamento com sucesso")
        void cadastrarMedicamentoComSucesso() {

            medicamentoDTO.setNome("Dorflex 2");
            medicamentoDTO.setCategoriaMedicamento(2);
            medicamentoDTO.setFormaFarmaceutica(2);
            medicamentoDTO.setDosagem(2.0);
            medicamentoDTO.setTipoDosagem(2);
            medicamentoDTO.setOidFabricante("1234");
            when(medicamentoRepository.findByOid(medicamentoDTO.getOid())).thenReturn(medicamento);
            when(medicamentoRepository.update(medicamento)).thenReturn(medicamento);

            medicamento = medicamentoService.atualizarMedicamento(medicamentoDTO, medicamentoDTO.getOid());

            assertNotNull(medicamento);
            Medicamento medicamento1 = medicamentoDTO.toEntity();
            medicamento1.setOidUsuarioCriacao(medicamento.getOidUsuarioCriacao());
            assertEquals(medicamento1, medicamento);
        }
    }

    @Nested
    @Order(3)
    @DisplayName("Deletar medicamento com sucesso")
    class DeletarMedicamentoTest {
        Medicamento medicamento;

        MedicamentoDTO medicamentoDTO;

        @BeforeEach
        void setup() {
            medicamentoDTO = new MedicamentoDTO();
            medicamentoDTO.setOid("123");
            medicamentoDTO.setNome("Dorflex");
            medicamentoDTO.setCategoriaMedicamento(1);
            medicamentoDTO.setFormaFarmaceutica(1);
            medicamentoDTO.setDosagem(1.0);
            medicamentoDTO.setTipoDosagem(1);
            medicamentoDTO.setOidFabricante("123");

            medicamento = medicamentoDTO.toEntity();
            medicamento.setOidUsuarioCriacao("teste");
            Sessao.getInstance().setOidUsuario("teste");
        }

        @Test
        @DisplayName("Deletar medicamento com sucesso")
        void deletarMedicamentoComSucesso() {
            when(medicamentoRepository.findByOid(anyString())).thenReturn(medicamento);
            medicamentoService.deletarMedicamento(medicamentoDTO.getOid());
            verify(medicamentoRepository, times(1)).delete(medicamento);
        }
    }

    @Nested
    @Order(4)
    @DisplayName("Buscar medicamentos")
    class BuscarMedicamentoTest {
        Medicamento medicamento;

        MedicamentoDTO medicamentoDTO;

        @BeforeEach
        void setup() {
            medicamentoDTO = new MedicamentoDTO();
            medicamentoDTO.setOid("123");
            medicamentoDTO.setNome("Dorflex");
            medicamentoDTO.setCategoriaMedicamento(1);
            medicamentoDTO.setFormaFarmaceutica(1);
            medicamentoDTO.setDosagem(1.0);
            medicamentoDTO.setTipoDosagem(1);
            medicamentoDTO.setOidFabricante("123");

            medicamento = medicamentoDTO.toEntity();
        }

    }
}



