package pl.luczak.michal.joboffersapp.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
class UserDAOConfig {

    @Bean
    UserDAOAdapter userDAOAdapter(
            UserRepository userRepository,
            UserDTOMapper userDTOMapper,
            UserEntityMapper userEntityMapper
    ) {
        return new UserDAOAdapter(userRepository, userDTOMapper, userEntityMapper);
    }
}
