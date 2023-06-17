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
    public void should_successfully_find_user() {
        //given
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .password("password")
                .username("username")
                .build();
        inMemoryDatabaseAdapter.save(userDTO);

        //when
        UserDTO user = loginAndSignUpFacade.findByUsername("username");

        //then
        Assertions.assertEquals(userDTO, user);
    }

    @Test
    public void should_unsuccessfully_find_user_and_throw_UserNotFoundException() {
        //then
        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> loginAndSignUpFacade.findByUsername("username")
        );
    }

    @Test
    public void should_successfully_register_user() {
        //given
        String username = "username";
        String password = "password";

        //when
        Long idRegisteredUser = loginAndSignUpFacade.register(username, password);
        UserDTO foundUserDTO = loginAndSignUpFacade.findByUsername(username);

        //then
        Assertions.assertEquals(idRegisteredUser, foundUserDTO.id());
        Assertions.assertEquals(username, foundUserDTO.username());
        Assertions.assertEquals(password, foundUserDTO.password());
    }

    @Test
    public void should_throw_UserAlreadyExistsException() {
        //given
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .password("password")
                .username("username")
                .build();

        //when
        inMemoryDatabaseAdapter.save(userDTO);

        //then
        Assertions.assertThrows(
                UsernameDuplicationException.class,
                () -> loginAndSignUpFacade.register(userDTO.username(), userDTO.password())
        );
    }
}