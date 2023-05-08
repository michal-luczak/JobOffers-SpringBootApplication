package pl.luczak.michal.joboffersapp.ports.input;

import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.Optional;

public interface UserDAOPort {

    Long save(UserDTO userDTO);

    Long delete(UserDTO userDTO);

    Optional<UserDTO> findByUsername(String username);
}
