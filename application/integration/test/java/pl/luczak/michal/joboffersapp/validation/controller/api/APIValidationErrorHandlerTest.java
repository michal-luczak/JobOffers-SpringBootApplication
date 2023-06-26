package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class APIValidationErrorHandlerTest extends AbstractIntegrationTest {

    @DynamicPropertySource
    protected static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("job-offers.offer.fetcher.port", wireMockServer::getPort);
    }

    @Value("${not.null}")
    private String fieldMustBeNotNull;

    @Value("${not.blank}")
    private String fieldMustBeNotBlank;

    // Tests for /register endpoint
    //     ||           ||
    //     \/           \/

    @Test
    void should_handle_BAD_REQUEST_caused_by_empty_content() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));
        //then
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(message, APIValidationErrorDTO.class);
        assertThat(apiErrorDTO.errors()).contains("Unreadable request content. Please use JSON format");
    }

    @Test
    void should_handle_BAD_REQUEST_caused_by_wrong_format_content() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON Format"));
        //then
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(message, APIValidationErrorDTO.class);
        assertThat(apiErrorDTO.errors()).contains("Unreadable request content. Please use JSON format");
    }

    @Test
    void should_handle_BAD_REQUEST_caused_by_missing_parameters_of_user_register_request() throws Exception {
        //given && when
        ResultActions responseWithEmptyContent = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));
        //then
        String message = responseWithEmptyContent.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(message, APIValidationErrorDTO.class);
        assertThat(apiErrorDTO.errors())
                .containsExactlyInAnyOrder(
                        String.format(fieldMustBeNotNull, "password"),
                        String.format(fieldMustBeNotNull, "username"),
                        String.format(fieldMustBeNotBlank, "password"),
                        String.format(fieldMustBeNotBlank, "username"));
    }

    // Tests for /offers endpoint
    //     ||           ||
    //     \/           \/

    @Test
    void should_handle_BAD_REQUEST_caused_by_wrong_type_of_endpoint_parameter() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(get("/offers/123"));
        //then
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(message, APIValidationErrorDTO.class);
        String expectedError = "Failed to convert value of type 'java.lang.String' to required type 'java.util.UUID'; Invalid UUID string: 123";
        assertThat(apiErrorDTO.errors()).contains(expectedError);
    }

    @Test
    @WithMockUser
    void should_handle_BAD_REQUEST_caused_by_missing_parameters_of_offer_post_request() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));
        //then
        String responseAsString = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(responseAsString, APIValidationErrorDTO.class);
        assertThat(apiErrorDTO.errors())
                .containsExactlyInAnyOrder(
                        String.format(fieldMustBeNotNull, "jobName"),
                        String.format(fieldMustBeNotBlank, "jobName"),
                        String.format(fieldMustBeNotNull, "companyName"),
                        String.format(fieldMustBeNotBlank, "companyName"),
                        String.format(fieldMustBeNotNull, "url"),
                        String.format(fieldMustBeNotBlank, "url"),
                        String.format(fieldMustBeNotNull, "salary"),
                        String.format(fieldMustBeNotBlank, "salary"));
    }

    @Test
    @WithMockUser
    void should_handle_BAD_REQUEST_caused_by_invalid_json_format_of_request_on_offers_endpoint() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON Format"));
        //then
        String responseAsString = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(responseAsString, APIValidationErrorDTO.class);
        assertThat(apiErrorDTO.errors()).contains("Unreadable request content. Please use JSON format");
    }

    // Tests for /token endpoint
    //     ||           ||
    //     \/           \/
    @Test
    void should_handle_BAD_REQUEST_caused_by_invalid_json_format_of_request_on_token_endpoint() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(post("/token"));
        //then
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(message, APIValidationErrorDTO.class);
        assertThat(apiErrorDTO.errors()).contains("Unreadable request content. Please use JSON format");
    }

    @Test
    void should_handle_BAD_REQUEST_caused_by_missing_parameters_of_token_request() throws Exception {
        //given && when
        ResultActions response = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));
        //then
        String responseAsString = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        APIValidationErrorDTO apiErrorDTO = objectMapper.readValue(responseAsString, APIValidationErrorDTO.class);
        assertThat(apiErrorDTO.errors())
                .containsExactlyInAnyOrder(
                        String.format(fieldMustBeNotNull, "username"),
                        String.format(fieldMustBeNotBlank, "username"),
                        String.format(fieldMustBeNotNull, "password"),
                        String.format(fieldMustBeNotBlank, "password"));
    }
}