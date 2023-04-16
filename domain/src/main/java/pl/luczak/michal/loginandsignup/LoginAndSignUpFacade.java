package pl.luczak.michal.loginandsignup;

import lombok.AllArgsConstructor;
import pl.luczak.michal.loginandsignup.dto.UserDTO;
import pl.luczak.michal.ports.UserPersistencePort;

@AllArgsConstructor
public class LoginAndSignUpFacade {

    private final UserPersistencePort userPersistencePort;

    public Long register(UserDTO userDTO) {
        if (userPersistencePort.findByUsername(userDTO.username()).isPresent()) {
            throw new UserAlreadyExistsException(userDTO.username());
        }
        return userPersistencePort.save(userDTO);
    }

    public UserDTO findByUsername(String username) {
        return userPersistencePort.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
