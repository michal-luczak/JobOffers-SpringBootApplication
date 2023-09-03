package pl.luczak.michal.joboffersapp.validation.controller.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class APIValidationErrorHandlerTest extends AbstractIntegrationTest {

    @DynamicPropertySource
    protected static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("job-offers.offer.fetcher.port", wireMockServer::getPort);
    }

    @Autowired
    private MessageSource messageSource;

    private String fieldMustBeNotNull;

    private String fieldMustBeNotBlank;

    private String invalidFormat;

    private String wrongLinkPattern;

    private String wrongUsernameSize;

    private String wrongPasswordSize;

    @BeforeEach
    void setUp() {
        this.fieldMustBeNotBlank = messageSource.getMessage(
                "not.blank", null, Locale.ENGLISH
        );
        this.fieldMustBeNotNull = messageSource.getMessage(
                "not.null", null, Locale.ENGLISH
        );
        this.invalidFormat = messageSource.getMessage(
                "invalid.format", null, Locale.ENGLISH
        );
        this.wrongLinkPattern = messageSource.getMessage(
                "wrong.link.pattern", null, Locale.ENGLISH
        );
        this.wrongUsernameSize = messageSource.getMessage(
                "wrong.size", null, Locale.ENGLISH
        );
        this.wrongPasswordSize = messageSource.getMessage(
                "wrong.size", null, Locale.ENGLISH
        );

        this.wrongUsernameSize = wrongUsernameSize.replace("{min}", "5")
                .replace("{max}", "25");
        this.wrongPasswordSize = wrongPasswordSize.replace("{min}", "8")
                .replace("{max}", "24");
    }

    // Tests for /register endpoint
    //     ||           ||
    //     \/           \/

    @Test
    void should_handle_BAD_REQUEST_caused_by_empty_content() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""));
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN
        assertThat(message).isEqualTo(invalidFormat);
    }

    @Test
    void should_handle_BAD_REQUEST_caused_by_wrong_format_content() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON Format"));
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN
        assertThat(message).isEqualTo(invalidFormat);
    }

    @Test
    void should_handle_BAD_REQUEST_caused_by_missing_parameters_of_user_register_request() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        // THEN
        response.andExpectAll(
                jsonPath(
                        "$.errors[?(@.field=='username')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                ),
                jsonPath(
                        "$.errors[?(@.field=='password')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                )
        );
    }

    // Tests for /offers endpoint
    //     ||           ||
    //     \/           \/

    @Test
    void should_handle_BAD_REQUEST_caused_by_wrong_type_of_endpoint_parameter() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(get("/offers/123"));
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedError = messageSource.getMessage(
                "invalid.type",
                new Object[] {"123", "UUID"},
                Locale.ENGLISH
        );

        // THEN
        assertThat(message).isEqualTo(expectedError);
    }

    @Test
    @WithMockUser
    void should_handle_BAD_REQUEST_caused_by_missing_parameters_of_offer_post_request() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        // THEN
        response.andExpectAll(
                jsonPath(
                        "$.errors[?(@.field=='url')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                ),
                jsonPath(
                        "$.errors[?(@.field=='salary')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                ),
                jsonPath(
                        "$.errors[?(@.field=='companyName')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                ),
                jsonPath(
                        "$.errors[?(@.field=='jobName')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                )
        );
    }

    @Test
    @WithMockUser
    void should_handle_BAD_REQUEST_caused_by_invalid_json_format_of_request_on_offers_endpoint() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Invalid JSON Format"));
        String responseAsString = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN
        assertThat(responseAsString).isEqualTo(invalidFormat);
    }

    // Tests for /token endpoint
    //     ||           ||
    //     \/           \/
    @Test
    void should_handle_BAD_REQUEST_caused_by_invalid_json_format_of_request_on_token_endpoint() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/token"));
        String message = response.andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN
        assertThat(message).isEqualTo(invalidFormat);
    }

    @Test
    void should_handle_BAD_REQUEST_caused_by_missing_parameters_of_token_request() throws Exception {
        // GIVEN && WHEN
        ResultActions response = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        // THEN
        response.andExpectAll(
                jsonPath(
                        "$.errors[?(@.field=='username')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                ),
                jsonPath(
                        "$.errors[?(@.field=='password')].messages[*]",
                        containsInAnyOrder(fieldMustBeNotBlank, fieldMustBeNotNull)
                )
        );
    }

    @Test
    @WithMockUser
    void should_handle_BAD_REQUEST_caused_by_wrong_link_format() throws Exception {
        // GIVEN
        String content = """
                    {
                        "offerUrl" : "wrong url format",
                        "salary" : "test",
                        "company" : "test",
                        "title" : "test"
                    }
                """.trim();

        // WHEN
        ResultActions response = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // THEN
        response.andExpectAll(
                jsonPath(
                        "$.errors[?(@.field=='url')].messages[*]",
                        contains(wrongLinkPattern)
                )
        );
    }

    @Test
    @WithMockUser
    void should_handle_BAD_REQUEST_caused_by_wrong_argument_size() throws Exception {
        // GIVEN
        String tooShortContent = """
                {
                    "username" : "abc",
                    "password" : "abc"
                }
                """.trim();
        String tooLongContent = """
                {
                    "username" : "tooLongUsernameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                    "password" : "tooLongPasswordddddddddddddddddddddddddddddddddddd"
                }
                """.trim();

        // WHEN
        ResultActions tooShortResponse = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tooShortContent));
        ResultActions tooLongResponse = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(tooLongContent));

        // THEN
        tooShortResponse.andExpectAll(
                jsonPath(
                        "$.errors[?(@.field=='username')].messages[*]",
                        contains(wrongUsernameSize)
                ),
                jsonPath(
                        "$.errors[?(@.field=='password')].messages[*]",
                        contains(wrongPasswordSize)
                )
        );
        tooLongResponse.andExpectAll(
                jsonPath(
                        "$.errors[?(@.field=='username')].messages[*]",
                        contains(wrongUsernameSize)
                ),
                jsonPath(
                        "$.errors[?(@.field=='password')].messages[*]",
                        contains(wrongPasswordSize)
                )
        );
    }
}