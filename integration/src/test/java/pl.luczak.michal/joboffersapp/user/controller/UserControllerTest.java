package pl.luczak.michal.joboffersapp.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;
import pl.luczak.michal.joboffersapp.ports.input.user.UserDAOPort;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDAOPort userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DynamicPropertySource
    protected static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", AbstractIntegrationTest.mongoDbContainer::getReplicaSetUrl);
        registry.add("spring.datasource.url", AbstractIntegrationTest.postgresContainer::getJdbcUrl);
        registry.add("job-offers.offer.fetcher.port", AbstractIntegrationTest.wireMockServer::getPort);
    }

    @Test
    void should_successfully_save_user_to_database() throws Exception {
        // given
        String username = "testUser";
        String password = "testPassword";

        // when
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                    {
                        "username": "%s",
                        "password": "%s"
                    }
                """.trim(), username, password)));

        // then
        String contentAsString = response.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDTO foundUser = userService.findByUsername(username);
        String foundEncodedPass = foundUser.password();

        assertAll(
                () -> assertThat(contentAsString).isEqualTo("1"),
                () -> assertThat(foundUser).isNotNull(),
                () -> assertThat(passwordEncoder.matches(password, foundEncodedPass)).isTrue(),
                () -> assertThat(foundUser.id()).isEqualTo(1),
                () -> assertThat(foundUser.username()).isEqualTo(username),
                () -> {
                    assertThat(userDAO.findByUsername(username).isPresent()).isTrue();
                    assertThat(userDAO.findByUsername(username).get()).isEqualTo(foundUser);
                }
        );
    }
}
