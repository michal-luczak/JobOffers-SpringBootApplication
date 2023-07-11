package pl.luczak.michal.joboffersapp;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JWTAuthTokenFilterTest {

    private MockHttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private JWTAuthTokenFilter jwtAuthTokenFilter;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        filterChain = Mockito.spy(new MockFilterChain());
        response = new MockHttpServletResponse();
        JWTConfigurationProperties jwtConfigurationProperties = new JWTConfigurationProperties(
                "testSecret",
                1000 * 60 * 60,
                "testIssuer"
        );
        jwtAuthTokenFilter = new JWTAuthTokenFilter(jwtConfigurationProperties);
    }

    @Test
    void doFilterInternal() throws ServletException, IOException {
        // GIVEN && WHEN
        jwtAuthTokenFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(filterChain, times(1))
                .doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void test() throws ServletException, IOException {
        // GIVEN
        request.addHeader("Authorization", "Bearer testToken");
        JWTVerifier jwtVerifier = Mockito.mock(JWTVerifier.class);
        DecodedJWT decodedJWTMock = mock(DecodedJWT.class);

        // WHEN
        when(jwtVerifier.verify("testToken")).thenReturn(decodedJWTMock);
        ReflectionTestUtils.setField(jwtAuthTokenFilter, "jwtVerifier", jwtVerifier);
        jwtAuthTokenFilter.doFilterInternal(request, response, filterChain);

        // THEN
        verify(filterChain).doFilter(request, response);
        assertEquals(decodedJWTMock.getSubject(), SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertNull(SecurityContextHolder.getContext().getAuthentication().getCredentials());
    }
}