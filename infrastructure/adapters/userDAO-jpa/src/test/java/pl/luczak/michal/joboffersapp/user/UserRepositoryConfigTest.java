package pl.luczak.michal.joboffersapp.user;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableJpaAuditing
@Configuration
@EntityScan("pl.luczak.michal.joboffersapp.user")
class UserRepositoryConfigTest {
}
