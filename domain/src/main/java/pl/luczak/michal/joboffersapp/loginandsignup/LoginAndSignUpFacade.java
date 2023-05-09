package pl.luczak.michal.joboffersapp.loginandsignup;

import lombok.AllArgsConstructor;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.RegistrationRequestDTO;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;
import pl.luczak.michal.joboffersapp.ports.input.UserDAOPort;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@AllArgsConstructor
public class LoginAndSignUpFacade implements UserService {

    private final UserDAOPort userDaoPort;

    @Override
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

    @Override
    public UserDTO findByUsername(String username) {
        return userDaoPort.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
