package pl.luczak.michal.joboffersapp.controller.register;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;
import pl.luczak.michal.joboffersapp.ports.input.user.UserControllerPort;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@RestController
@AllArgsConstructor
class UserController implements UserControllerPort<ResponseEntity<?>, UserRegisterRequest> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Override
    public ResponseEntity<Long> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(
                        userRegisterRequest.username(),
                        hashedPassword
                ));
    }
}
