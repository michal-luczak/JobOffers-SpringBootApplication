package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = TokenRestController.class)
@ContextConfiguration(classes = TokenRestControllerTestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class TokenRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JWTAuthenticator jwtAuthenticator;

    @Test
    void should_successfully() throws Exception {
        LoginRequestDTO offerSaveRequest = new LoginRequestDTO(
                "testUsername",
                "testPassword"
        );
        mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offerSaveRequest))
                .characterEncoding(StandardCharsets.UTF_8));
        verify(jwtAuthenticator, times(1))
                .authenticateAndGenerateToken(Mockito.any());
    }
}