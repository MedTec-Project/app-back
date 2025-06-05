package br.medtec.unit;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.features.user.LoginService;
import br.medtec.features.user.User;
import br.medtec.features.user.UserDTO;
import br.medtec.features.user.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("User Test (Registration and Login)")
public class    LoginServiceTest {

    @InjectMocks
    LoginService loginServiceMock;

    @Mock
    UserRepository userRepository;

    @DisplayName("Login Test")
    @Nested
    class LoginTest {
        UserDTO userDTO;
        User user;

        @BeforeEach
        void setup() {
            userDTO = new UserDTO();
            userDTO.setOid("123");
            userDTO.setEmail("richard.fernandes@gmail.com");
            userDTO.setPassword("31232132132");
            userDTO.setPhone("12345678911");
            userDTO.setName("Richard");

            user = new User();
            user.setPassword("31232132132");
            user.setEmail("richard.fernandes@gmail.com");
            user.setPhone("12345678911");
            user.setName("Richard");
            user.setOid("123");
            user.setAdmin(false);
        }

        @Test
        @DisplayName("Check if User Exists")
        void checkUserExistsSuccessfully() {
            when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(user);
            Boolean result = loginServiceMock.checkIfExists(userDTO);
            assertTrue(result);
        }

        @Test
        @DisplayName("Check if User Does Not Exist")
        void checkUserExistsFailure() {
            when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
            Boolean result = loginServiceMock.checkIfExists(userDTO);
            assertFalse(result);
        }

        @Test
        @DisplayName("Successful Login")
        void loginSuccessfully() {
            when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(user);
            String token = loginServiceMock.login(userDTO);
            assertNotNull(token);
            verify(userRepository, times(2)).findByEmail(userDTO.getEmail());
        }

        @Test
        @DisplayName("Login Failure (User Does Not Exist)")
        void loginUserDoesNotExist() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                loginServiceMock.login(userDTO);
            });
        }
    }

    @DisplayName("Registration Test")
    @Nested
    class RegistrationTest {

        UserDTO userDTO;

        @BeforeEach
        void setup() {
            userDTO = new UserDTO();
            userDTO.setEmail("teste@gmail.com");
            userDTO.setPassword("123456");
            userDTO.setPhone("12345678911");
            userDTO.setName("Richard");
        }

        @Test
        @DisplayName("Successful User Registration")
        void registerUserSuccessfully() {
            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setName(userDTO.getName());
            user.setPhone(userDTO.getPhone());
            User registeredUser = loginServiceMock.createUser(userDTO);

            assertNotNull(registeredUser);
            assertEquals(user.getEmail(), registeredUser.getEmail());
            assertEquals(user.getName(), registeredUser.getName());
            assertEquals(user.getPassword(), registeredUser.getPassword());
            assertEquals(user.getPhone(), registeredUser.getPhone());
        }

        @Test
        @DisplayName("User Registration Failure (No Email)")
        void registerUserFailureNoEmail() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setEmail(null);
                loginServiceMock.createUser(userDTO);
            });
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setEmail("");
                loginServiceMock.createUser(userDTO);
            });
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setEmail("   ");
                loginServiceMock.createUser(userDTO);
            });

            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("User Registration Failure (No Password)")
        void registerUserFailureNoPassword() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPassword(null);
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPassword("");
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPassword("   ");
                loginServiceMock.createUser(userDTO);
            });

            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("User Registration Failure (No Name)")
        void registerUserFailureNoName() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setName(null);
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setName("");
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setName("   ");
                loginServiceMock.createUser(userDTO);
            });

            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("User Registration Failure (No Phone)")
        void registerUserFailureNoPhone() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPhone(null);
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPhone("");
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPhone("   ");
                loginServiceMock.createUser(userDTO);
            });

            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("User Registration Failure (Invalid Phone)")
        void registerUserFailureInvalidPhone() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPhone("123456789");
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setPhone("123456789111");
                loginServiceMock.createUser(userDTO);
            });

            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("User Registration Failure (Invalid Email)")
        void registerUserFailureInvalidEmail() {
            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setEmail("teste");
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setEmail("teste@");
                loginServiceMock.createUser(userDTO);
            });

            assertThrows(MEDBadRequestExecption.class, () -> {
                userDTO.setEmail("teste@.com");
                loginServiceMock.createUser(userDTO);
            });

            verify(userRepository, never()).save(any());
        }
    }
}
