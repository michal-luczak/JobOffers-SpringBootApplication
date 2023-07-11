package pl.luczak.michal.joboffersapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOMapperTest {

    private UserDTOMapper userDTOMapper;
    private UserDTO userDTO;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        this.userDTOMapper = new UserDTOMapper();
        Long id = 1L;
        String username = "username";
        String password = "password";
        this.userDTO = UserDTO.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();
        this.userEntity = new UserEntity(id, username, password);
    }

    @Test
    void apply() {
        // GIVEN && WHEN
        UserDTO applied = userDTOMapper.apply(userEntity);

        // THEN
        assertThat(applied).isEqualTo(userDTO);
    }

    @Test
    void map() {
        // GIVEN && WHEN
        UserDTO mapped = userDTOMapper.map(userEntity);

        // THEN
        assertThat(mapped).isEqualTo(userDTO);
    }
}