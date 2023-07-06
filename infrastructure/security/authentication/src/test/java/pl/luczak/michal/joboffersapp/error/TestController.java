package pl.luczak.michal.joboffersapp.error;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.luczak.michal.joboffersapp.LoginRequestDTO;

@RestController
class TestController {

    @PostMapping("/token")
    BadCredentialsResponseDTO testEndpointToTestValidationHandler(@RequestBody LoginRequestDTO body) {
        if (body.username().equals("testUsername")) {
            throw new BadCredentialsException("Wrong password") {};
        }
        throw new AuthenticationException("User with username: " + body.username() + " not found") {};
    }
}
