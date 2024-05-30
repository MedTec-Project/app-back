package br.medtec.services;

import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.exceptions.MEDValidationExecption;
import br.medtec.repositories.LoginRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
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

    @Mock
    EntityManager entityManager;


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
            Mockito.when(loginRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(usuario);
            Boolean resultado = loginServiceMock.verificaExiste(usuarioDTO, true);
            Assertions.assertTrue(resultado);
        }

        @Test
        @DisplayName("Verifica Usuario Não Existente")
        void verificaUsuarioExisteComFalha() {
            Boolean resultado = loginServiceMock.verificaExiste(usuarioDTO, true);
            Assertions.assertFalse(resultado);
        }

        @Test
        @DisplayName("Login com sucesso")
        void loginComSucesso(){
            Mockito.when(loginRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(usuario);
            String token = loginServiceMock.login(usuarioDTO);
            Assertions.assertNotNull(token);
            Mockito.verify(loginRepository, Mockito.times(1)).findByEmail(usuarioDTO.getEmail());
        }

        @Test
        @DisplayName("Login com falha (usuario não existe)")
        void loginUsuarioNaoExiste(){
            Mockito.when(loginRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(null);
            Assertions.assertThrows(MEDBadRequestExecption.class, () -> {
                loginServiceMock.login(usuarioDTO);
            });
        }

    }

    @DisplayName("Teste Cadastro")
    @Nested
    class CadastroTest {

        UsuarioDTO usuarioDTO;

        @BeforeEach
        void setup(){
            usuarioDTO = new UsuarioDTO();
            usuarioDTO.setEmail("teste@gmail.com");
            usuarioDTO.setSenha("123456");
            usuarioDTO.setTelefone("12345678911");
            usuarioDTO.setNome("Richard");

        }

        @Test
        @DisplayName("Usuario com sucesso")
        void cadastraUsuarioComSucesso(){


            Usuario usuario = new Usuario();
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha(usuarioDTO.getSenha());
            usuario.setNome(usuarioDTO.getNome());
            usuario.setTelefone(usuarioDTO.getTelefone());
            Usuario usuarioCadastrado = loginServiceMock.criaUsuario(usuarioDTO);

            Assertions.assertNotNull(usuarioCadastrado);
            Assertions.assertEquals(usuario.getEmail(), usuarioCadastrado.getEmail());
            Assertions.assertEquals(usuario.getNome(), usuarioCadastrado.getNome());
            Assertions.assertEquals(usuario.getSenha(), usuarioCadastrado.getSenha());
            Assertions.assertEquals(usuario.getTelefone(), usuarioCadastrado.getTelefone());


        }

        @Test
        @DisplayName("Usuario sem email")
        void cadastraUsuarioComFalhaSemEmail(){
           Assertions.assertThrows(MEDValidationExecption.class, () -> {
               usuarioDTO.setEmail(null);
               loginServiceMock.criaUsuario(usuarioDTO);
           });
           Assertions.assertThrows(MEDValidationExecption.class, () -> {
               usuarioDTO.setEmail("");
               loginServiceMock.criaUsuario(usuarioDTO);
           });
           Assertions.assertThrows(MEDValidationExecption.class, () -> {
               usuarioDTO.setEmail("   ");
               loginServiceMock.criaUsuario(usuarioDTO);
           });

           Mockito.verify(loginRepository, Mockito.never()).persist(Mockito.any());

        }

        @Test
        @DisplayName("Usuario sem senha")
        void cadastraUsuarioComFalhaSemSenha(){
            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setSenha(null);
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setSenha("");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setSenha("   ");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Mockito.verify(loginRepository, Mockito.never()).persist(Mockito.any());
        }

        @Test
        @DisplayName("Usuario sem nome")
        void cadastraUsuarioComFalhaSemNome(){

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setNome(null);
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setNome("");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setNome("   ");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Mockito.verify(loginRepository, Mockito.never()).persist(Mockito.any());

        }

        @Test
        @DisplayName("Usuario sem telefone")
        void cadastraUsuarioComFalhaSemTelefone(){

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setTelefone(null);
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setTelefone("");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setTelefone("   ");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Mockito.verify(loginRepository, Mockito.never()).persist(Mockito.any());

        }

        @Test
        @DisplayName("Usuario telefone invalido")
        void cadastraUsuarioComFalhaTelefoneInvalido(){

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setTelefone("123456789");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setTelefone("123456789111");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Mockito.verify(loginRepository, Mockito.never()).persist(Mockito.any());

        }

        @Test
        @DisplayName("Usuario email invalido")
        void cadastraUsuarioComFalhaEmailInvalido(){

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setEmail("teste");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setEmail("teste@");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Assertions.assertThrows(MEDValidationExecption.class, () -> {
                usuarioDTO.setEmail("teste@.com");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            Mockito.verify(loginRepository, Mockito.never()).persist(Mockito.any());

        }
    }
}
