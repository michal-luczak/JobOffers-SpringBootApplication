package pl.luczak.michal.joboffersapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

@Configuration
class SecurityConfig {

    @Bean
    JWTAuthTokenFilter jwtAuthTokenFilter(JWTConfigurationProperties jwtConfigurationProperties) {
        return new JWTAuthTokenFilter(jwtConfigurationProperties);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    UserDetailsService userDetailsService(
            UserService userService,
            UserDTOMapper userDTOMapper
    ) {
        return new LoginUserDetailsService(userService, userDTOMapper);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JWTAuthTokenFilter jwtAuthTokenFilter) throws Exception {
        httpSecurity.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/offers/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/token").permitAll()
                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers().cacheControl().disable()
                .frameOptions().disable()
                .xssProtection().disable()
                .contentTypeOptions().disable()
                .and()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    UserDTOMapper userDetailsToUserDTOMapper() {
        return new UserDTOMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
