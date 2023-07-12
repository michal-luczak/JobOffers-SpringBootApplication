package pl.luczak.michal.joboffersapp.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {
    private static final String MESSAGE_SOURCE_PATH = "classpath:/messages";

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE_PATH);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}