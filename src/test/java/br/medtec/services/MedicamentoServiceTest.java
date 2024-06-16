package br.medtec.services;

import br.medtec.dto.MedicamentoDTO;
import br.medtec.entity.Medicamento;
import br.medtec.exceptions.MEDValidationExecption;
import br.medtec.medicamento.MedicamentoRepository;
import br.medtec.medicamento.MedicamentoService;
import br.medtec.utils.EntityUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Teste Medicamento")
@ExtendWith(MockitoExtension.class)
public class MedicamentoServiceTest {

    @InjectMocks
    MedicamentoService medicamentoService;

    @Mock
    MedicamentoRepository medicamentoRepository;

    @Mock
    EntityUtils entityUtils;

    @Mock
    EntityManager entityManager;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
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
            medicamento = new Medicamento();
            medicamento.setNome("Dorflex");
            medicamento.setCategoriaMedicamento(Medicamento.CategoriaMedicamento.ANALGESICO);
            medicamento.setFormaFarmaceutica(Medicamento.FormaFarmaceutica.COMPRIMIDO);
            medicamento.setDosagem(1.0);
            medicamento.setOidFabricante("123");

            entityUtils.setManager(entityManager);
        }

        @Test
        @DisplayName("Cadastrar medicamento com sucesso")
        void cadastrarMedicamentoComSucesso() {
            medicamento = medicamentoService.cadastrarMedicamento(medicamentoDTO);
            Assertions.assertNotNull(medicamento);
            Assertions.assertEquals(medicamentoDTO.getNome(), medicamento.getNome());
            Assertions.assertEquals(Medicamento.CategoriaMedicamento.valueOf(medicamentoDTO.getCategoriaMedicamento()), medicamento.getCategoriaMedicamento());
            Assertions.assertEquals(Medicamento.FormaFarmaceutica.valueOf(medicamentoDTO.getFormaFarmaceutica()), medicamento.getFormaFarmaceutica());
            Assertions.assertEquals(medicamentoDTO.getDosagem(), medicamento.getDosagem());
            Assertions.assertEquals(Medicamento.TipoDosagem.valueOf(medicamentoDTO.getTipoDosagem()), medicamento.getTipoDosagem());
            Assertions.assertEquals(medicamentoDTO.getOidFabricante(), medicamento.getOidFabricante());
        }

        @Test
        @DisplayName("Cadastrar medicamento com nome vazio")
        void cadastrarMedicamentoComNomeVazio() {

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setNome("");
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setNome(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setNome(" ");
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Cadastrar medicamento com categoria vazia")
        void cadastrarMedicamentoComCategoriaVazia() {
            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setCategoriaMedicamento(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setCategoriaMedicamento(999);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Cadastrar medicamento com forma farmaceutica vazia")
        void cadastrarMedicamentoComFormaFarmaceuticaVazia() {
            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setFormaFarmaceutica(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setFormaFarmaceutica(999);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Cadastrar medicamento com dosagem vazia")
        void cadastrarMedicamentoComDosagemVazia() {
            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setDosagem(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setDosagem(0.0);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

//        @Test
//        @DisplayName("Cadastrar medicamento com fabricante vazio")
//        void cadastrarMedicamentoComFabricanteVazio() {
//            Assertions.assertThrows(MEDValidationExecption.class, () -> {
//                medicamentoDTO.setOidFabricante("");
//                medicamentoService.cadastrarMedicamento(medicamentoDTO);
//            });
//
//            Assertions.assertThrows(MEDValidationExecption.class, () -> {
//                medicamentoDTO.setOidFabricante(null);
//                medicamentoService.cadastrarMedicamento(medicamentoDTO);
//            });
//
//            Assertions.assertThrows(MEDValidationExecption.class, () -> {
//                medicamentoDTO.setOidFabricante(" ");
//                medicamentoService.cadastrarMedicamento(medicamentoDTO);
//            });
//        }

    }

    @Nested
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
        }

        @Test
        @DisplayName("Editar medicamento com sucesso")
        void cadastrarMedicamentoComSucesso() {
            medicamento = medicamentoService.cadastrarMedicamento(medicamentoDTO);
            Assertions.assertNotNull(medicamento);
            Assertions.assertEquals(medicamentoDTO.getNome(), medicamento.getNome());
            Assertions.assertEquals(Medicamento.CategoriaMedicamento.valueOf(medicamentoDTO.getCategoriaMedicamento()), medicamento.getCategoriaMedicamento());
            Assertions.assertEquals(Medicamento.FormaFarmaceutica.valueOf(medicamentoDTO.getFormaFarmaceutica()), medicamento.getFormaFarmaceutica());
            Assertions.assertEquals(medicamentoDTO.getDosagem(), medicamento.getDosagem());
            Assertions.assertEquals(Medicamento.TipoDosagem.valueOf(medicamentoDTO.getTipoDosagem()), medicamento.getTipoDosagem());
            Assertions.assertEquals(medicamentoDTO.getOidFabricante(), medicamento.getOidFabricante());
        }

        @Test
        @DisplayName("Editar medicamento com nome vazio")
        void cadastrarMedicamentoComNomeVazio() {

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setNome("");
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setNome(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setNome(" ");
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Editar medicamento com categoria vazia")
        void cadastrarMedicamentoComCategoriaVazia() {
            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setCategoriaMedicamento(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setCategoriaMedicamento(999);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

        }

        @Test
        @DisplayName("Editar medicamento com forma farmaceutica vazia")
        void cadastrarMedicamentoComFormaFarmaceuticaVazia() {
            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setFormaFarmaceutica(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setFormaFarmaceutica(999);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }

        @Test
        @DisplayName("Editar medicamento com dosagem vazia")
        void cadastrarMedicamentoComDosagemVazia() {
            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setDosagem(null);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                medicamentoDTO.setDosagem(0.0);
                medicamentoService.cadastrarMedicamento(medicamentoDTO);
            });
        }
    }
}



//
//    @Test
//    @DisplayName("Deletar medicamento com sucesso")
//    void cadastrarMedicamentoComSucesso() {
//    }
//
//    @Test
//    @DisplayName("Cadastro de medicamento com sucesso")
//    void cadastrarMedicamentoComSucesso() {
//    }


