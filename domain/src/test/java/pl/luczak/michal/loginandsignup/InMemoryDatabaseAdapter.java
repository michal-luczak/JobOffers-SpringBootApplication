package pl.luczak.michal.loginandsignup;

import pl.luczak.michal.loginandsignup.dto.UserDTO;
import pl.luczak.michal.ports.UserDAOPort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryDatabaseAdapter implements UserDAOPort {

    private final Map<Long, UserDTO> users = new HashMap<>();

    @Override
    public Long save(UserDTO userDTO) {
        users.put(userDTO.id(), userDTO);
        return userDTO.id();
    }

    @Override
    public Long delete(UserDTO userDTO) {
        users.remove(userDTO.id());
        return userDTO.id();
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return users.values()
                .stream()
                .filter(userDTO -> userDTO.username().equals(username))
                .findFirst();
    }
}
