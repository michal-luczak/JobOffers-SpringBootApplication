package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.luczak.michal.joboffersapp.ports.output.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = UserControllerTestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    void should_successfully_register_user_by_invoking_register_method_in_UserService() throws Exception {
        // GIVEN
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                "testUsername",
                "testPassword"
        );

        // WHEN
        ResultActions resultActions = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterRequest)));

        // THEN
        verify(userService, times(1))
                .register(Mockito.any(), Mockito.any());
        assertThat(resultActions.andReturn()
                .getResponse()
                .getStatus()
        ).isEqualTo(201);
    }
}