package pl.luczak.michal.loginandsignup.facade;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.loginandsignup.LoginAndSignUpFacade;
import pl.luczak.michal.ports.UserDAOPort;

@Configuration
class LoginAndSignUpFacadeConfig {

    @Bean
    LoginAndSignUpFacade loginAndSignUpFacade(UserDAOPort userDAOPort) {
        return new LoginAndSignUpFacade(userDAOPort);
    }
}
