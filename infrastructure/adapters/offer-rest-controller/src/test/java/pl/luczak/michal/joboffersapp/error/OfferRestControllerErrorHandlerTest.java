package pl.luczak.michal.joboffersapp.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.luczak.michal.joboffersapp.OfferSaveRequest;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
@ContextConfiguration(classes = OfferRestControllerErrorHandlerTestConfig.class)
class OfferRestControllerErrorHandlerTest {

    private MockMvc mockMvc;

    @Autowired
    private TestController testController;

    @Autowired
    private OfferRestControllerErrorHandler offerErrorHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(testController)
                .setControllerAdvice(offerErrorHandler)
                .build();
    }

    @Test
    void should_handle_OfferNotFoundException() throws Exception {
        // GIVEN
        UUID uniqueID = UUID.randomUUID();

        // WHEN
        ResultActions resultActions = mockMvc.perform(get("/test-exception/" + uniqueID));
        String contentAsString = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        OfferErrorResponseDTO offerErrorResponseDTO = objectMapper.readValue(contentAsString, OfferErrorResponseDTO.class);

        // THEN
        assertAll(() -> {
            assertThat(offerErrorResponseDTO.message()).isEqualTo(String.format("Offer with uniqueID: %s not found", uniqueID));
            assertThat(offerErrorResponseDTO.status()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(resultActions.andReturn()
                            .getResponse()
                            .getStatus()
            ).isEqualTo(404);
        });
    }

    @Test
    void should_handle_OfferAlreadyExistsException() throws Exception {
        // GIVEN
        OfferSaveRequest offerSaveRequest = OfferSaveRequest.builder()
                .url("https://testJobUrl.pl")
                .jobName("testJobName")
                .salary("testSalary")
                .companyName("testCompanyName")
                .build();

        // WHEN
        ResultActions resultActions = mockMvc.perform(post("/test-exception")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offerSaveRequest)));
        String responseContent = resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        OfferErrorResponseDTO offerErrorResponseDTO = objectMapper.readValue(responseContent, OfferErrorResponseDTO.class);

        // THEN
        assertAll(() -> {
            assertThat(offerErrorResponseDTO.message()).isEqualTo("Test response message");
            assertThat(offerErrorResponseDTO.status()).isEqualTo(HttpStatus.CONFLICT);
            assertThat(resultActions.andReturn()
                            .getResponse()
                            .getStatus()
            ).isEqualTo(409);
        });
    }
}