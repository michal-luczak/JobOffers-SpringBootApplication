package pl.luczak.michal.joboffersapp.error;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
abstract class OfferRestControllerErrorHandlerTestConfig {

    @Bean
    TestController testController() {
        return new TestController();
    }

    @Bean
    OfferRestControllerErrorHandler offerRestControllerErrorHandler() {
        return new OfferRestControllerErrorHandler();
    }
}
