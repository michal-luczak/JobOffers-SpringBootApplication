package pl.luczak.michal.joboffersapp.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.luczak.michal.joboffersapp.ports.input.user.UserDAOPort;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories
class UserDAOConfig {

    @Bean
    UserDAOPort userDAOAdapter(
            UserRepository userRepository,
            UserDTOMapper userDTOMapper,
            UserEntityMapper userEntityMapper
    ) {
        return new UserDAOAdapter(userRepository, userDTOMapper, userEntityMapper);
    }
}
