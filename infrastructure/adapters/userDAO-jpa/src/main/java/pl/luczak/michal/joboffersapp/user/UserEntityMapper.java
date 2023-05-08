package pl.luczak.michal.joboffersapp.user;

import org.springframework.stereotype.Service;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.function.Function;

@Service
class UserEntityMapper implements Function<UserDTO, UserEntity> {

    @Override
    public UserEntity apply(UserDTO userDTO) {
        return new UserEntity(
                userDTO.id(),
                userDTO.username(),
                userDTO.password()
        );
    }

    UserEntity map(UserDTO userDTO) {
        return apply(userDTO);
    }
}
