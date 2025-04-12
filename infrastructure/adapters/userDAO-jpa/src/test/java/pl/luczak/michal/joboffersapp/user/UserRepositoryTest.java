package pl.luczak.michal.joboffersapp.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = UserRepositoryConfigTest.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_successfully_save_user_and_then_find_user_by_username() {
        // GIVEN
        UserEntity userEntity = new UserEntity("testUsername", "testPassword");

        // WHEN
        userRepository.save(userEntity);
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(userEntity.getUsername());

        // THEN
        assertThat(optionalUserEntity).isPresent().contains(userEntity);
    }

    @Test
    void should_thrown_an_exception_while_trying_to_find_nonexistent_user() {
        // GIVEN && WHEN
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername("nonexistentUser");

        // THEN
        assertThat(optionalUserEntity).isEmpty();
    }

    @Test
    void should_throw_an_exception_while_trying_to_save_duplicated_username() {
        // GIVEN
        UserEntity firstUserEntity = new UserEntity(null, "testUsername", "testPassword");
        UserEntity secondUserEntity = new UserEntity(null, "testUsername", "testPassword");

        // WHEN
        userRepository.save(firstUserEntity);

        // THEN
        assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.save(secondUserEntity)
        );
    }
}