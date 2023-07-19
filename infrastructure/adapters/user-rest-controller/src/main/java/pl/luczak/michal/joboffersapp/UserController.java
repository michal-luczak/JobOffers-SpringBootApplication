package pl.luczak.michal.joboffersapp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.ports.input.user.UserControllerPort;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@RestController
@AllArgsConstructor
@Log4j2
class UserController implements UserControllerPort<ResponseEntity<?>, UserRegisterRequest> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Override
    public ResponseEntity<Long> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        log.warn("User with username: {} trying to register", userRegisterRequest.username());
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.password());
        Long register = userService.register(
                userRegisterRequest.username(),
                hashedPassword
        );
        log.info("Sending response to user with id of registered user");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(register);
    }
}
