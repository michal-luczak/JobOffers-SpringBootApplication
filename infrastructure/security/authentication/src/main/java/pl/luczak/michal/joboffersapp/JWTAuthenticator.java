package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@AllArgsConstructor
class JWTAuthenticator {

    private final AuthenticationManager authenticationManager;

    JWTResponseDTO authenticateAndGenerateToken(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.username(),
                        loginRequestDTO.password()
                )
        );
        return new JWTResponseDTO("", "");
    }
}
