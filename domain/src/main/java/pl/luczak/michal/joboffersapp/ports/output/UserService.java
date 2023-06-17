package pl.luczak.michal.joboffersapp.ports.output;

import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

public interface UserService {

    UserDTO findByUsername(String username);

    Long register(String username, String password);
}
