package pl.luczak.michal.joboffersapp.loginandsignup;

import lombok.AllArgsConstructor;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.RegistrationRequestDTO;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;
import pl.luczak.michal.joboffersapp.ports.input.UserDAOPort;

@AllArgsConstructor
public class LoginAndSignUpFacade {

    private final UserDAOPort userDaoPort;

    public Long register(RegistrationRequestDTO registrationRequest) {
        String username = registrationRequest.username();
        String password = registrationRequest.password();
        if (userDaoPort.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(password);
        }
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(password)
                .build();
        return userDaoPort.save(userDTO);
    }

    public UserDTO findByUsername(String username) {
        return userDaoPort.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
