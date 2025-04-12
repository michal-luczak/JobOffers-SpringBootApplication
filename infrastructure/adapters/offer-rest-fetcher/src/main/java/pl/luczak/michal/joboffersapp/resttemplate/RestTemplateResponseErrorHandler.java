package pl.luczak.michal.joboffersapp.resttemplate;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@Log4j2
class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    protected void handleError(ClientHttpResponse response, HttpStatusCode statusCode, URI url, HttpMethod method) {
        if (statusCode.is5xxServerError()) {
            log.error("Error 5xx while using using http client");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while using http client");
        } else if (statusCode.is4xxClientError()) {
            if (statusCode == HttpStatus.NOT_FOUND) {
                log.error("Error 404 while using using http client");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else if (statusCode == HttpStatus.UNAUTHORIZED) {
                log.error("Error 401 while using using http client");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}
