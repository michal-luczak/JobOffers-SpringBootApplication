package pl.luczak.michal.joboffersapp.user;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import pl.luczak.michal.joboffersapp.loginandsignup.UserIdDuplicationException;
import pl.luczak.michal.joboffersapp.ports.input.user.UserDAOPort;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
@Log4j2
class UserDAOAdapter implements UserDAOPort {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Long save(UserDTO userDTO) {
        UserEntity userEntity = userEntityMapper.map(userDTO);
        UserEntity savedUser;
        try {
            log.warn("Trying to save user: {}...", userDTO);
            savedUser = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException exception) {
            String message = "Cannot save user because this user is existing";
            log.error(message);
            throw new UserIdDuplicationException(message);
        }
        log.info("Successfully saved user: {}", userDTO);
        return savedUser.getId();
    }

    @Override
    public Long delete(UserDTO userDTO) {
        UserEntity userEntity = userEntityMapper.map(userDTO);
        log.warn("Trying to delete user: {}...", userDTO);
        Long userID = Stream.of(userEntity)
                .map(user -> {
                    userRepository.delete(userEntity);
                    return user.getId();
                }).findFirst()
                .orElseThrow();
        log.info("Successfully deleted user: {}", userDTO);
        return userID;
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        log.warn("Trying to find user with username: {}...", username);
        Optional<UserDTO> userDTO = userRepository.findByUsername(username)
                .map(userDTOMapper);
        log.info("Successfully found user with username: {}", username);
        return userDTO;
    }
}
