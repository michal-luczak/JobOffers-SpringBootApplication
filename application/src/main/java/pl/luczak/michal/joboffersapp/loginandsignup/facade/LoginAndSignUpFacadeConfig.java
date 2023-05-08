package pl.luczak.michal.joboffersapp.loginandsignup.facade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.joboffersapp.ports.input.UserDAOPort;
import pl.luczak.michal.joboffersapp.loginandsignup.LoginAndSignUpFacade;

@Configuration
class LoginAndSignUpFacadeConfig {

    @Bean
    LoginAndSignUpFacade loginAndSignUpFacade(UserDAOPort userDAOPort) {
        return new LoginAndSignUpFacade(userDAOPort);
    }
}
