package pl.luczak.michal.joboffersapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOMapperTest {

    private UserDTO userDTO;
    private User user;
    private UserDTOMapper userDTOMapper;

    @BeforeEach
    void setUp() {
        userDTOMapper = new UserDTOMapper();
        String username = "testUsername";
        String password = "testPassword";
        user = new User(username, password, Collections.emptyList());
        userDTO = UserDTO.builder()
                .id(1L)
                .username(username)
                .password(password)
                .build();
    }

    @Test
    void fromUserDTO() {
        // GIVEN && WHEN
        User fromUserDTO = userDTOMapper.fromUserDTO(userDTO);

        // THEN
        assertThat(fromUserDTO).isEqualTo(user);
    }

    @Test
    void apply() {
        // GIVEN && WHEN
        UserDTO fromUser = userDTOMapper.apply(user);

        // THEN
        assertThat(fromUser).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userDTO);
    }
}