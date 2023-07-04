package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(classes = APIValidationErrorHandlerUnitTestConfig.class)
@SpringBootTest
class APIValidationErrorHandlerUnitTest {

    @Autowired
    private TestController testController;

    private MockMvc mockMvc;

    @Autowired
    private APIValidationErrorHandler apiValidationErrorHandler;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setControllerAdvice(apiValidationErrorHandler)
                .build();
    }

    @Test
    void should_handle_MethodArgumentNotValidException() throws Exception {
        String contentAsString = mockMvc.perform(post("/test-exception")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(contentAsString).contains("testMessage for testValue field");
    }

    @Test
    void should_handle_HttpMessageNotReadableException() throws Exception {
        String contentAsString = mockMvc.perform(post("/test-exception")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(contentAsString).contains("Unreadable request content. Please use JSON format");
    }

    @Test
    void should_handle_MethodArgumentTypeMismatchException() throws Exception {
        String contentAsString = mockMvc.perform(get("/test-exception/abc"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(contentAsString).contains("Failed to convert input: abc to Integer");
    }
}
