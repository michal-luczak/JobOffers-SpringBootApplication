package pl.luczak.michal.joboffersapp;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

class TestAuthenticationManagerMock implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!authentication.getPrincipal().equals("testUsername")) {
            throw new AuthenticationException("User with username: " + authentication.getPrincipal() + " not found") {};
        }
        if (!authentication.getCredentials().equals("testPassword")) {
            throw new BadCredentialsException("Wrong password");
        }
        User user = new User(
                authentication.getPrincipal().toString(),
                authentication.getCredentials().toString(),
                Collections.emptyList()
        );
        return new UsernamePasswordAuthenticationToken(
                user,
                authentication.getCredentials()
        );
    }
}
