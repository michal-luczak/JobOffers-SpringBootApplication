package pl.luczak.michal.joboffersapp.ports.input.user;

import pl.luczak.michal.joboffersapp.loginandsignup.UserIdDuplicationException;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.Optional;

public interface UserDAOPort {

    Long save(UserDTO userDTO) throws UserIdDuplicationException;

    Long delete(UserDTO userDTO);

    Optional<UserDTO> findByUsername(String username);
}
