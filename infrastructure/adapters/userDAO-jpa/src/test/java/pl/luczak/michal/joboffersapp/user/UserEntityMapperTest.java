package pl.luczak.michal.joboffersapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityMapperTest {

    private UserEntityMapper userEntityMapper;
    private UserDTO userDTO;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        this.userEntityMapper = new UserEntityMapper();
        String username = "testUsername";
        String password = "testPassword";
        Long id = 1L;
        userDTO = UserDTO.builder()
                .username(username)
                .password(password)
                .id(id)
                .build();
        userEntity = new UserEntity(id, username, password);
    }

    @Test
    void should_successfully_apply_to_UserDTO() {
        // GIVEN && WHEN
        UserEntity applied = userEntityMapper.apply(userDTO);

        // THEN
        assertThat(applied).isEqualTo(userEntity);
    }

    @Test
    void should_successfully_map_to_UserDTO() {
        // GIVEN && WHEN
        UserEntity applied = userEntityMapper.map(userDTO);

        // THEN
        assertThat(applied).isEqualTo(userEntity);
    }
}