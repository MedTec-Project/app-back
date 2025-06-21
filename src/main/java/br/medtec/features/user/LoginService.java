package br.medtec.features.user;

import br.medtec.exceptions.MEDBadRequestExecption;
import br.medtec.utils.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class LoginService {
    private UserRepository userRepository;

    @Inject
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public String login(UserDTO userDTO) {
        if (checkIfExists(userDTO)) {
            User user = userRepository.findByEmail(userDTO.getEmail());
            if (!user.checkPassword(userDTO.getPassword())) {
                throw new MEDBadRequestExecption("Email ou Senha Incorreto");
            }
            UserSession.setSession(user);
            return JWTUtils.generateToken(user);
        } else {
            log.warn("Email ou Senha Incorreto {}", userDTO.getEmail());
            throw new MEDBadRequestExecption("Email ou Senha Incorreto");
        }
    }

    @Transactional
    public String createUser(UserDTO userDTO) {
        validateUser(userDTO);
        User newUser = userDTO.toEntity();
        userRepository.save(newUser);
        UserSession.setSession(newUser);
        return JWTUtils.generateToken(newUser);
    }

    @Transactional
    public Boolean checkIfExists(UserDTO userDTO) {
        if (userDTO != null) {
            User user = userRepository.findByEmail(userDTO.getEmail());
            return user != null;
        }
        return null;
    }

    @Transactional
    public void validateUser(UserDTO userDTO) {
        Validations validations = new Validations();

        if (userDTO == null) {
            validations.add("Usuario não pode ser nulo");
        }

        if (!StringUtil.isValidString(userDTO.getEmail()) || !StringUtil.isValidEmail(userDTO.getEmail())) {
            validations.add("Email Invalido");
        }

        if (!StringUtil.isValidString(userDTO.getPassword())) {
            validations.add("Senha Invalida");
        }

        if (!StringUtil.isValidString(userDTO.getName())) {
            validations.add("Nome Invalido");
        }

        if ((!StringUtil.isValidString(userDTO.getPhone())) || (!StringUtil.isValidPhone(userDTO.getPhone()))) {
            validations.add("Telefone Invalido");
        }

        validations.throwErrors();
    }
}
