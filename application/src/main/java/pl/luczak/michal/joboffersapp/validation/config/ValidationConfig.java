package pl.luczak.michal.joboffersapp.validation.config;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@AllArgsConstructor
public class ValidationConfig {
    private final MessageSource validationMessageSource;

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(validationMessageSource);
        return bean;
    }
}
