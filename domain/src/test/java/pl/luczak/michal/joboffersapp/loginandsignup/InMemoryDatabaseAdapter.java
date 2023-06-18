package pl.luczak.michal.joboffersapp.loginandsignup;

import lombok.NonNull;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;
import pl.luczak.michal.joboffersapp.ports.input.user.UserDAOPort;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

class InMemoryDatabaseAdapter implements UserDAOPort {

    private final Map<Long, UserDTO> users = new HashMap<>();

    @Override
    public Long save(@NonNull UserDTO userDTO) {
        if (users.containsKey(userDTO.id())) {
            throw new UserIdDuplicationException(
                    String.format(
                            "User with id: %s already exists",
                            userDTO.id()
                    )
            );
        }
        users.values()
                .stream()
                .filter(user -> user.username().equals(userDTO.username()))
                .findFirst()
                .ifPresent(u -> {
                    throw new UsernameDuplicationException(
                            String.format(
                                    "User with username: %s already exists",
                                    userDTO.username()
                            )
                    );
                });
        UserDTO userToSave = userDTO.toBuilder().build();
        if (userDTO.id() == null) {
            userToSave = userDTO.toBuilder().id(1L).build();
            Iterator<Long> iterator = users.keySet().iterator();
            if (iterator.hasNext()) {
                userToSave = userDTO.toBuilder().id(iterator.next()).build();
            }
        }
        users.put(userToSave.id(), userToSave);
        return userToSave.id();
    }

    @Override
    public Long delete(@NonNull UserDTO userDTO) {
        users.remove(userDTO.id());
        return userDTO.id();
    }

    @Override
    public Optional<UserDTO> findByUsername(@NonNull String username) {
        return users.values()
                .stream()
                .filter(userDTO -> userDTO.username().equals(username))
                .findFirst();
    }
}
