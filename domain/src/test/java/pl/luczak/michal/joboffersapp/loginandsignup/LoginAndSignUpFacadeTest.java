package pl.luczak.michal.joboffersapp.loginandsignup;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

class LoginAndSignUpFacadeTest {

    private final InMemoryDatabaseAdapter inMemoryDatabaseAdapter = new InMemoryDatabaseAdapter();

    private final LoginAndSignUpFacade loginAndSignUpFacade = new LoginAndSignUpFacade(
            inMemoryDatabaseAdapter
    );

    @Test
    void should_successfully_find_user() {
        // GIVEN
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .password("password")
                .username("username")
                .build();
        inMemoryDatabaseAdapter.save(userDTO);

        // WHEN
        UserDTO user = loginAndSignUpFacade.findByUsername("username");

        // THEN
        Assertions.assertEquals(userDTO, user);
    }

    @Test
    void should_unsuccessfully_find_user_and_throw_UserNotFoundException() {
        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> loginAndSignUpFacade.findByUsername("username")
        );
    }

    @Test
    void should_successfully_register_user() {
        // GIVEN
        String username = "username";
        String password = "password";

        // WHEN
        Long idRegisteredUser = loginAndSignUpFacade.register(username, password);
        UserDTO foundUserDTO = loginAndSignUpFacade.findByUsername(username);

        // THEN
        Assertions.assertEquals(idRegisteredUser, foundUserDTO.id());
        Assertions.assertEquals(username, foundUserDTO.username());
        Assertions.assertEquals(password, foundUserDTO.password());
    }

    @Test
    void should_throw_UserAlreadyExistsException() {
        // GIVEN
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .password("password")
                .username("username")
                .build();

        // WHEN
        inMemoryDatabaseAdapter.save(userDTO);

        // THEN
        var username = userDTO.username();
        var password = userDTO.password();
        Assertions.assertThrows(
                UsernameDuplicationException.class,
                () -> loginAndSignUpFacade.register(username, password)
        );
    }
}