package pl.luczak.michal.joboffersapp.validation.controller.token;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;
import pl.luczak.michal.joboffersapp.error.BadCredentialsResponseDTO;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TokenControllerErrorHandlerTest extends AbstractIntegrationTest {

    @DynamicPropertySource
    protected static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("job-offers.offer.fetcher.port", wireMockServer::getPort);
    }

    @Autowired
    private UserService userService;

    @Test
    void should_return_UNAUTHORIZED_because_of_nonexistent_user_in_database() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "username": "username",
                        "password": "password"
                    }
                """.trim()));
        //then
        String responseAsString = response.andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BadCredentialsResponseDTO apiErrorDTO = objectMapper.readValue(responseAsString, BadCredentialsResponseDTO.class);
        assertThat(apiErrorDTO.errors()).contains("User with username: username not found");
    }

    @Test
    void should_return_UNAUTHORIZED_because_of_wrong_password_to_given_user() throws Exception {
        //given
        userService.register("testUser", "testPassword");
        //when
        ResultActions response = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "username": "testUser",
                        "password": "wrongPassword"
                    }
                """.trim()));
        //then
        String responseAsString = response.andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BadCredentialsResponseDTO apiErrorDTO = objectMapper.readValue(responseAsString, BadCredentialsResponseDTO.class);
        assertThat(apiErrorDTO.errors()).contains("Wrong password for user with username: testUser");
    }
}
