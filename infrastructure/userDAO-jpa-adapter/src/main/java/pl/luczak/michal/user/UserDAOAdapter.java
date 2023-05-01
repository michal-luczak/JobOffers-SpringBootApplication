package pl.luczak.michal.user;

import lombok.AllArgsConstructor;
import pl.luczak.michal.loginandsignup.dto.UserDTO;
import pl.luczak.michal.ports.UserDAOPort;

import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
class UserDAOAdapter implements UserDAOPort {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Long save(UserDTO userDTO) {
        UserEntity userEntity = userEntityMapper.map(userDTO);
        return Stream.of(userEntity)
                .map(user -> {
                    userRepository.save(userEntity);
                    return user.getId();
                }).findFirst()
                .orElseThrow();
    }

    @Override
    public Long delete(UserDTO userDTO) {
        UserEntity userEntity = userEntityMapper.map(userDTO);
        return Stream.of(userEntity)
                .map(user -> {
                    userRepository.delete(userEntity);
                    return user.getId();
                }).findFirst()
                .orElseThrow();
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userDTOMapper);
    }
}
