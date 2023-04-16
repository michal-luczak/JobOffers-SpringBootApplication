package pl.luczak.michal.ports;

import pl.luczak.michal.loginandsignup.dto.UserDTO;

import java.util.Optional;

public interface UserPersistencePort {

    Long save(UserDTO userDTO);

    Long delete(UserDTO userDTO);

    Optional<UserDTO> findByUsername(String username);
}
