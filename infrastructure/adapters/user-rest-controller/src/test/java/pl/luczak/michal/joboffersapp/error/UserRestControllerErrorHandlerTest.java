package pl.luczak.michal.joboffersapp.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
@ContextConfiguration(classes = UserRestControllerErrorHandlerTestConfig.class)
class UserRestControllerErrorHandlerTest {

    private MockMvc mockMvc;

    @Autowired
    private TestController testController;

    @Autowired
    private UserRestControllerErrorHandler userRestControllerErrorHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setControllerAdvice(userRestControllerErrorHandler)
                .build();
    }

    @Test
    void should_handle_UserIdDuplicationException() throws Exception {
        // GIVEN && WHEN
        ResultActions resultActions = mockMvc.perform(post("/test-exception"));
        String contentAsString = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        UserAlreadyExistsResponse offerErrorResponseDTO = objectMapper
                .readValue(
                    contentAsString,
                    UserAlreadyExistsResponse.class
                );

        // THEN
        assertThat(offerErrorResponseDTO.status()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(offerErrorResponseDTO.message()).isEqualTo("Test response message");
    }
}