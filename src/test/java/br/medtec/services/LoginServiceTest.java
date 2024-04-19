package br.medtec.services;

import br.medtec.dto.UsuarioDTO;
import br.medtec.entity.Usuario;
import br.medtec.repositories.LoginRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@QuarkusTest
@DisplayName("Teste Usuario (Cadastro e Login)")
public class LoginServiceTest {

    @InjectMock
    LoginRepository loginRepository;

    @InjectMock
    LoginService loginServiceMock;

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
            usuarioDTO.setEmail("richard.fernandes@gmail.com");
            usuarioDTO.setSenha("31232132132");
            usuarioDTO.setTelefone("12345678911");
            usuarioDTO.setNome("Richard");
            usuario = new Usuario();
            usuario.setEmail("richard.fernandes@gmail.com");
            usuario.setSenha("31232132132");
            usuario.setTelefone("12345678911");
            usuario.setNome("Richard");
        }

        @Test
        void verificaUsuarioExisteComSucesso() {
            Mockito.when(loginRepository.findByEmailAndSenha(usuarioDTO.getEmail())).thenReturn(usuario);
            Usuario usuarioResponse = loginRepository.findByEmailAndSenha(usuario.getEmail());
            Assertions.assertEquals(usuarioResponse, usuario);
            Mockito.verify(loginRepository, Mockito.times(1)).findByEmailAndSenha(usuarioDTO.getEmail());
        }

    }

    @DisplayName("Teste Cadastro")
    @Nested
    class CadastroTest {}








}
