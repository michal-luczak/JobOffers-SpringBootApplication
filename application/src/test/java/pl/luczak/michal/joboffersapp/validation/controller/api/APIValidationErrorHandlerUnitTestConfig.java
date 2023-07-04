package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
abstract class APIValidationErrorHandlerUnitTestConfig {

    @Bean
    APIValidationErrorHandler apiValidationErrorHandler() {
        return new APIValidationErrorHandler();
    }

    @Bean
    TestController testController() {
        return new TestController();
    }
}
