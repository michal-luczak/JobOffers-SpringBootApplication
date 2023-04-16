package pl.luczak.michal.loginandsignup;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.luczak.michal.loginandsignup.dto.UserDTO;

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
        Assertions.assertEquals(user, userDTO);
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
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .password("password")
                .username("username")
                .build();

        //when
        Long idRegisteredUser = loginAndSignUpFacade.register(userDTO);
        UserDTO foundUserDTO = loginAndSignUpFacade.findByUsername(userDTO.username());

        //then
        Assertions.assertEquals(idRegisteredUser, userDTO.id());
        Assertions.assertEquals(foundUserDTO, userDTO);
    }

    @Test
    public void should_successfully_register_user_and_throw_UserAlreadyExistsException() {
        //given
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .password("password")
                .username("username")
                .build();
        inMemoryDatabaseAdapter.save(userDTO);

        //then
        Assertions.assertThrows(
                UserAlreadyExistsException.class,
                () -> loginAndSignUpFacade.register(userDTO)
        );
    }
}