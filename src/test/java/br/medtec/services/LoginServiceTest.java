package br.medtec.services;

import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.repositories.LoginRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("Teste Usuario (Cadastro e Login)")
public class LoginServiceTest {
    @InjectMocks
    LoginService loginServiceMock;

    @Mock
    LoginRepository loginRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Teste Login")
    @Nested
    class LoginTest {
        UsuarioDTO usuarioDTO;
        Usuario usuario;

        @BeforeEach
        void setup(){
            usuarioDTO = new UsuarioDTO();
            usuarioDTO.setOid("123");
            usuarioDTO.setEmail("richard.fernandes@gmail.com");
            usuarioDTO.setSenha("31232132132");
            usuarioDTO.setTelefone("12345678911");
            usuarioDTO.setNome("Richard");

            usuario = new Usuario();
            usuario.setSenha("31232132132");
        }

        @Test
        @DisplayName("Verifica Usuario Existe")
        void verificaUsuarioExisteComSucesso() {
            Mockito.when(loginRepository.findByEmailAndSenha(usuarioDTO.getEmail())).thenReturn(usuario);
            Boolean resultado = loginServiceMock.verificaExiste(usuarioDTO, true);
            Assertions.assertTrue(resultado);
        }

        @Test
        @DisplayName("Verifica Usuario Não Existente")
        void verificaUsuarioExisteComFalha() {
            Boolean resultado = loginServiceMock.verificaExiste(usuarioDTO, true);
            Assertions.assertFalse(resultado);
        }

    }

    @DisplayName("Teste Cadastro")
    @Nested
    class CadastroTest {}








}
