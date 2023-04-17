package pl.luczak.michal.loginandsignup;

import lombok.AllArgsConstructor;
import pl.luczak.michal.loginandsignup.dto.RegistrationRequestDTO;
import pl.luczak.michal.loginandsignup.dto.UserDTO;
import pl.luczak.michal.ports.UserDAO;

@AllArgsConstructor
public class LoginAndSignUpFacade {

    private final UserDAO userDAO;

    public Long register(RegistrationRequestDTO registrationRequest) {
        String username = registrationRequest.username();
        String password = registrationRequest.password();
        if (userDAO.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(password);
        }
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(password)
                .build();
        return userDAO.save(userDTO);
    }

    public UserDTO findByUsername(String username) {
        return userDAO.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
