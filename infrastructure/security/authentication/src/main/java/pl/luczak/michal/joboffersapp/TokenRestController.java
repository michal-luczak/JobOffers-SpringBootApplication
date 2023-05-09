package pl.luczak.michal.joboffersapp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
class TokenRestController {

    private final JWTAuthenticator jwtAuthenticator;

    @PostMapping
    ResponseEntity<JWTResponseDTO> authenticateAndGenerateToken(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        JWTResponseDTO jwtResponseDTO = jwtAuthenticator.authenticateAndGenerateToken(loginRequestDTO);
        return ResponseEntity.ok(jwtResponseDTO);
    }
}
