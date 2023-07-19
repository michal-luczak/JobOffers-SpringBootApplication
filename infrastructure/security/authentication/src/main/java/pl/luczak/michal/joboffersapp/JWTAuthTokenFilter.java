package pl.luczak.michal.joboffersapp;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Log4j2
class JWTAuthTokenFilter extends OncePerRequestFilter {

    private final JWTVerifier jwtVerifier;

    public JWTAuthTokenFilter(JWTConfigurationProperties properties) {
        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.warn("JWTAuthTokenFilter has been triggered");
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            filterChain.doFilter(request, response);
            log.info("JWTAuthTokenFilter successfully handed over request to another filter without authentication because of empty Authorization header");
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = buildUsernamePassAuthToken(authorization);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
        log.info("JWTAuthTokenFilter successfully handed over request to another filter with authentication because of empty Authorization header");
    }

    private UsernamePasswordAuthenticationToken buildUsernamePassAuthToken(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token.substring(7));
        return new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, Collections.emptyList());
    }
}
