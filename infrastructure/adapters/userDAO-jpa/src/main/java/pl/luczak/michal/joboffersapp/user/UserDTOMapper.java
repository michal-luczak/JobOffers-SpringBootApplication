package pl.luczak.michal.joboffersapp.user;

import org.springframework.stereotype.Service;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.function.Function;

@Service
class UserDTOMapper implements Function<UserEntity, UserDTO> {

    @Override
    public UserDTO apply(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

    UserDTO map(UserEntity userEntity) {
        return apply(userEntity);
    }
}
