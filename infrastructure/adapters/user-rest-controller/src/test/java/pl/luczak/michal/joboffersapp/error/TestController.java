package pl.luczak.michal.joboffersapp.error;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.luczak.michal.joboffersapp.loginandsignup.UserIdDuplicationException;

@RestController
class TestController {

    @PostMapping("/test-exception")
    UserAlreadyExistsResponse testEndpointToTestValidationHandler() {
        throw new UserIdDuplicationException("Test response message");
    }
}
