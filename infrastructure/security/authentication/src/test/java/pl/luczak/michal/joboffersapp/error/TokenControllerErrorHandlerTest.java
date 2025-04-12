package pl.luczak.michal.joboffersapp.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.luczak.michal.joboffersapp.LoginRequestDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
@ContextConfiguration(classes = TokenControllerErrorHandlerTestConfig.class)
class TokenControllerErrorHandlerTest {

    private MockMvc mockMvc;

    @Autowired
    private TestController testController;

    @Autowired
    private TokenControllerErrorHandler tokenControllerErrorHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setControllerAdvice(tokenControllerErrorHandler)
                .build();
    }

    @Test
    void should_successfully_handle_AuthenticationException() throws Exception {
        // GIVEN
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                "nonexistentUser",
                "nonexistentPassword"
        );

        // WHEN
        ResultActions resultActions = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDTO)));
        String contentAsString = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        BadCredentialsResponseDTO badCredentialsResponseDTO = objectMapper
                .readValue(
                        contentAsString,
                        BadCredentialsResponseDTO.class
                );

        // THEN
        assertThat(badCredentialsResponseDTO.errors())
                .containsExactlyInAnyOrder("User with username: nonexistentUser not found");
    }

    @Test
    void should_successfully_handle_BadCredentialsException() throws Exception {
        //GIVEN
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(
                "testUsername",
                "wrongPassword"
        );

        // WHEN
        ResultActions resultActions = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDTO)));
        String contentAsString = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        BadCredentialsResponseDTO badCredentialsResponseDTO = objectMapper
                .readValue(
                        contentAsString,
                        BadCredentialsResponseDTO.class
                );

        // THEN
        assertThat(badCredentialsResponseDTO.errors())
                .containsExactlyInAnyOrder("Wrong password");
    }
}