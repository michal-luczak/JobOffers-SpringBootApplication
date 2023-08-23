package pl.luczak.michal.joboffersapp.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {

    @Bean
    public GroupedOpenApi customOpenAPI() {
        return GroupedOpenApi.builder()
                .group("job-offers")
                .pathsToMatch("/job-offers/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPIConfiguration() {
        String serverHost = "michal-luczak.pl";
        return new OpenAPI().addServersItem(new Server().url(serverHost));
    }
}
