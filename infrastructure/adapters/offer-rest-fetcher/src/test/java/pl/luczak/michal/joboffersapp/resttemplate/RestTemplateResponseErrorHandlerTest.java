package pl.luczak.michal.joboffersapp.resttemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.EnumSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RestTemplateResponseErrorHandlerTest {

    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @BeforeEach
    void setUp() {
        this.restTemplateResponseErrorHandler = new RestTemplateResponseErrorHandler();
    }

    @Test
    void should_handle_5xx_http_code() {
        // GIVEN
        EnumSet<HttpStatus> enumSet = createEnumSet(HttpStatus::is5xxServerError);
        String expectedMessage = "Error while using http client";

        // WHEN && THEN
        enumSet.forEach(status ->
                testTemplate(
                        expectedMessage,
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
        );
    }

    private EnumSet<HttpStatus> createEnumSet(Predicate<HttpStatus> predicate) {
        EnumSet<HttpStatus> enumSet = EnumSet.allOf(HttpStatus.class);
        return EnumSet.copyOf(
                enumSet.stream()
                        .filter(predicate)
                        .collect(Collectors.toSet())
        );
    }

    private void testTemplate(String message, HttpStatus responseStatus) {
        // GIVEN
        ClientHttpResponse response = Mockito.mock(ClientHttpResponse.class);

        // WHEN
        try {
            when(response.getStatusCode()).thenReturn(responseStatus);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // THEN
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> restTemplateResponseErrorHandler.handleError(response)
        );
        assertThat(exception.getMessage())
                .contains(message);
        assertThat(exception.getStatusCode())
                .isEqualTo(responseStatus);
    }

    @Test
    void should_handle_404_http_code() {
        // GIVEN
        EnumSet<HttpStatus> enumSet = EnumSet.of(HttpStatus.NOT_FOUND);

        // WHEN && THEN
        enumSet.forEach(status -> testTemplate("", HttpStatus.NOT_FOUND));
    }

    @Test
    void should_handle_401_http_code() {
        // GIVEN
        EnumSet<HttpStatus> enumSet = EnumSet.of(HttpStatus.UNAUTHORIZED);

        // WHEN && THEN
        enumSet.forEach(status -> testTemplate("", HttpStatus.UNAUTHORIZED));
    }
}