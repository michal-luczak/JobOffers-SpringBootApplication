package pl.luczak.michal.joboffersapp.loginandsignup.facade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.joboffersapp.ports.input.user.UserDAOPort;
import pl.luczak.michal.joboffersapp.loginandsignup.LoginAndSignUpFacade;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@Configuration
class LoginAndSignUpFacadeConfig {

    @Bean
    UserService loginAndSignUpFacade(UserDAOPort userDAOPort) {
        return new LoginAndSignUpFacade(userDAOPort);
    }
}
