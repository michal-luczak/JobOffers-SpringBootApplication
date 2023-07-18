package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.luczak.michal.joboffersapp.message.MessageConfig;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = {
        APIValidationErrorHandlerUnitTestConfig.class,
        MessageConfig.class
})
@SpringBootTest
class APIValidationErrorHandlerUnitTest {

    @Autowired
    private TestController testController;

    @Autowired
    private MessageSource messageSource;

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
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/test-exception")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        // THEN
        response.andExpect(
                jsonPath(
                        "$.errors[?(@.field=='testValue')].messages[*]",
                        containsInAnyOrder("testMessage")
                )
        );
    }

    @Test
    void should_handle_HttpMessageNotReadableException() throws Exception {
        // GIVEN && WHEN
        String contentAsString = mockMvc.perform(post("/test-exception")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN
        assertThat(contentAsString)
                .contains(messageSource.getMessage("invalid.format", null, Locale.ENGLISH));
    }

    @Test
    void should_handle_MethodArgumentTypeMismatchException() throws Exception {
        // GIVEN && WHEN
        String contentAsString = mockMvc.perform(get("/test-exception/abc"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        Object firstArg = "abc";
        Object secondArg = "Integer";
        Object[] args = new Object[] {firstArg, secondArg};

        // THEN
        assertThat(contentAsString)
                .contains(messageSource.getMessage("invalid.type", args, Locale.ENGLISH));
    }
}
