package pl.luczak.michal.joboffersapp;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@Configuration
abstract class UserControllerTestConfig {

    @Bean
    UserController userController(
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        return new UserController(userService, passwordEncoder);
    }

    @Bean
    UserService offerService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
