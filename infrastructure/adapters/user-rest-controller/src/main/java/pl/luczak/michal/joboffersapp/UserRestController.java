package pl.luczak.michal.joboffersapp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;
import pl.luczak.michal.joboffersapp.ports.input.UserController;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
class UserRestController implements UserController<ResponseEntity<?>, UserRegisterRequest> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/{username}")
    @Override
    public ResponseEntity<UserDTO> findByUsername(@PathVariable @Valid String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

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
