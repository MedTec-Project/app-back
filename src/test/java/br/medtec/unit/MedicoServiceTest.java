package br.medtec.unit;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.medico.*;
import br.medtec.utils.Sessao;
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
@DisplayName("Teste Medico")
@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @InjectMocks
    MedicoService medicoService;

    @Mock
    MedicoRepository medicoRepository;

    @BeforeAll
    void setup() { MockitoAnnotations.openMocks(this); }

    @Nested
    @Order(1)
    @DisplayName("Cadastro de medico")
    class CadastroMedicoTest {

        Medico medico;

        MedicoDTO medicoDTO;

        @BeforeEach
        void setup() {
            medicoDTO = new MedicoDTO();
            medicoDTO.setNome("Richard");
            medicoDTO.setCrm("123");
            medicoDTO.setTelefone("12345678911");
            medico = medicoDTO.toEntity();
        }

        @Test
        @DisplayName("Cadastrar medico com sucesso")
        void cadastrarMedicoComSucesso() {
            medico = medicoService.criarMedico(medicoDTO);
            assertEquals(medico, medicoDTO.toEntity());
            verify(medicoRepository, times(1)).save(medico);
        }

        @Test
        @DisplayName("Cadastrar medico com crm existente")
        void cadastrarMedicoComErro() {
            when(medicoRepository.existsByCrm(anyString())).thenReturn(true);
            assertThrows(MEDBadRequestExecption.class, () -> medicoService.criarMedico(medicoDTO));
            verify(medicoRepository, times(0)).save(any());
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Atualizacao de medico")
    class AtualizacaoMedicoTest {
        Medico medico;

        MedicoDTO medicoDTO;

        @BeforeEach
        void setup() {
            medicoDTO = new MedicoDTO();
            medicoDTO.setOid("123");
            medicoDTO.setNome("Richard");
            medicoDTO.setCrm("123");
            medicoDTO.setTelefone("12345678911");
            medico = medicoDTO.toEntity();
//            medicoRepository.ent
        }

        @Test
        @DisplayName("Atualizar medico com sucesso")
        void atualizarMedicoComSucesso() {
            when(medicoRepository.findByOid(anyString())).thenReturn(medico);
            when(medicoRepository.update(any())).thenReturn(medico);
            medico = medicoService.atualizarMedico(medicoDTO);
            assertEquals(medico, medicoDTO.toEntity());
            verify(medicoRepository, times(1)).update(medico);
        }

        @Test
        @DisplayName("Atualizar medico com oid inexistente")
        void atualizarMedicoComOidInexistente() {
            when(medicoRepository.findByOid(anyString())).thenThrow(new MEDBadRequestExecption("Medico não encontrado"));
            assertThrows(MEDBadRequestExecption.class, () -> medicoService.atualizarMedico(medicoDTO));
            verify(medicoRepository, times(0)).update(any());
        }
    }

    @Nested
    @Order(3)
    @DisplayName("Buscar medico")
    class BuscarMedicoTest {
        Medico medico;

        @BeforeEach
        void setup() {
            medico = new Medico();
            medico.setOid("123");
            medico.setNome("Richard");
            medico.setCrm("123");
            medico.setTelefone("12345678911");
            medico.setOidUsuarioCriacao("123");
        }

        @Test
        @DisplayName("Buscar medico com sucesso")
        void buscarMedicoComSucesso() {
            when(medicoRepository.findByOid(anyString())).thenReturn(medico);
            Sessao.getInstance().setOidUsuario("123");

            Medico medicoBuscado = medicoService.buscarMedico("123");

            assertEquals(medico, medicoBuscado);
            verify(medicoRepository, times(1)).findByOid("123");
        }

        @Test
        @DisplayName("Buscar medico com oid inexistente")
        void buscarMedicoComOidInexistente() {
            when(medicoRepository.findByOid(anyString())).thenThrow(new MEDBadRequestExecption("Medico não encontrado"));

            assertThrows(MEDBadRequestExecption.class, () -> medicoService.buscarMedico("123"));

            verify(medicoRepository, times(1)).findByOid("123");
        }

        @Test
        @DisplayName("Buscar medico com oid diferente do usuario logado")
        void buscarMedicoComOidDiferenteDoUsuarioLogado() {
            when(medicoRepository.findByOid(anyString())).thenReturn(medico);
            Sessao.getInstance().setOidUsuario("456");

            assertThrows(MEDBadRequestExecption.class, () -> medicoService.buscarMedico("123"));

            verify(medicoRepository, times(1)).findByOid("123");
        }
    }
}