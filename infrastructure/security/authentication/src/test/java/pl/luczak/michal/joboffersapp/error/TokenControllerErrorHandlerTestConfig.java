package pl.luczak.michal.joboffersapp.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
abstract class TokenControllerErrorHandlerTestConfig {

    @Bean
    TestController testController() {
        return new TestController();
    }

    @Bean
    TokenControllerErrorHandler tokenControllerErrorHandler() {
        return new TokenControllerErrorHandler();
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
