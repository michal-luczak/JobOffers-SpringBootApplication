package pl.luczak.michal.joboffersapp.error;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
abstract class UserRestControllerErrorHandlerTestConfig {

    @Bean
    TestController testController() {
        return new TestController();
    }

    @Bean
    UserRestControllerErrorHandler offerRestControllerErrorHandler() {
        return new UserRestControllerErrorHandler();
    }
}
