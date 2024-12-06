package br.medtec.unit;

import br.medtec.features.usuario.LoginService;
import br.medtec.features.usuario.Usuario;
import br.medtec.features.usuario.UsuarioDTO;
import br.medtec.features.usuario.UsuarioRepository;
import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.exceptions.MEDBadRequestExecption;
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
@ExtendWith(MockitoExtension.class)
@DisplayName("Teste Usuario (Cadastro e Login)")
public class LoginServiceTest {

    @InjectMocks
    LoginService loginServiceMock;

    @Mock
    UsuarioRepository usuarioRepository;



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
            MockitoAnnotations.openMocks(this);
            usuarioDTO = new UsuarioDTO();
            usuarioDTO.setOid("123");
            usuarioDTO.setEmail("richard.fernandes@gmail.com");
            usuarioDTO.setSenha("31232132132");
            usuarioDTO.setTelefone("12345678911");
            usuarioDTO.setNome("Richard");

            usuario = new Usuario();
            usuario.setSenha("31232132132");
            usuario.setEmail("richard.fernandes@gmail.com");
            usuario.setTelefone("12345678911");
            usuario.setNome("Richard");
            usuario.setOid("123");
            usuario.setAdministrador(false);
        }

        @Test
        @DisplayName("Verifica Usuario Existe")
        void verificaUsuarioExisteComSucesso() {
            when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(usuario);
            Boolean resultado = loginServiceMock.verificaExiste(usuarioDTO);
            assertTrue(resultado);
        }

        @Test
        @DisplayName("Verifica Usuario Não Existente")
        void verificaUsuarioExisteComFalha() {
          when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(null);
            Boolean resultado = loginServiceMock.verificaExiste(usuarioDTO);
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Login com sucesso")
        void loginComSucesso(){
            when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(usuario);
            String token = loginServiceMock.login(usuarioDTO);
            assertNotNull(token);
            verify(usuarioRepository, times(2)).findByEmail(usuarioDTO.getEmail());
        }

        @Test
        @DisplayName("Login com falha (usuario não existe)")
        void loginUsuarioNaoExiste(){
            assertThrows(MEDBadRequestExecption.class, () -> {
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

            assertNotNull(usuarioCadastrado);
            assertEquals(usuario.getEmail(), usuarioCadastrado.getEmail());
            assertEquals(usuario.getNome(), usuarioCadastrado.getNome());
            assertEquals(usuario.getSenha(), usuarioCadastrado.getSenha());
            assertEquals(usuario.getTelefone(), usuarioCadastrado.getTelefone());


        }

        @Test
        @DisplayName("Usuario sem email")
        void cadastraUsuarioComFalhaSemEmail(){
           assertThrows(MEDBadRequestExecption.class, () -> {
               usuarioDTO.setEmail(null);
               loginServiceMock.criaUsuario(usuarioDTO);
           });
           assertThrows(MEDBadRequestExecption.class, () -> {
               usuarioDTO.setEmail("");
               loginServiceMock.criaUsuario(usuarioDTO);
           });
           assertThrows(MEDBadRequestExecption.class, () -> {
               usuarioDTO.setEmail("   ");
               loginServiceMock.criaUsuario(usuarioDTO);
           });

           verify(usuarioRepository, never()).save(any());

        }

        @Test
        @DisplayName("Usuario sem senha")
        void cadastraUsuarioComFalhaSemSenha(){
            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setSenha(null);
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setSenha("");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setSenha("   ");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            verify(usuarioRepository, never()).save(any());
        }

        @Test
        @DisplayName("Usuario sem nome")
        void cadastraUsuarioComFalhaSemNome(){

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setNome(null);
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setNome("");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setNome("   ");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            verify(usuarioRepository, never()).save(any());

        }

        @Test
        @DisplayName("Usuario sem telefone")
        void cadastraUsuarioComFalhaSemTelefone(){

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setTelefone(null);
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setTelefone("");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setTelefone("   ");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            verify(usuarioRepository, never()).save(any());

        }

        @Test
        @DisplayName("Usuario telefone invalido")
        void cadastraUsuarioComFalhaTelefoneInvalido(){

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setTelefone("123456789");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setTelefone("123456789111");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            verify(usuarioRepository, never()).save(any());

        }

        @Test
        @DisplayName("Usuario email invalido")
        void cadastraUsuarioComFalhaEmailInvalido(){

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setEmail("teste");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setEmail("teste@");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                usuarioDTO.setEmail("teste@.com");
                loginServiceMock.criaUsuario(usuarioDTO);
            });

            verify(usuarioRepository, never()).save(any());

        }
    }
}
