package pl.luczak.michal.joboffersapp;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@AllArgsConstructor
@Log4j2
class TokenRestController {

    private final JWTAuthenticator jwtAuthenticator;

    @PostMapping
    ResponseEntity<JWTResponseDTO> authenticateAndGenerateToken(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        log.warn("User with username: {} is trying to get token", loginRequestDTO.username());
        JWTResponseDTO jwtResponseDTO = jwtAuthenticator.authenticateAndGenerateToken(loginRequestDTO);
        log.info("User with username: {} has successfully got the token", loginRequestDTO.username());
        return ResponseEntity.ok(jwtResponseDTO);
    }
}
