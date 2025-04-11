package pl.luczak.michal.joboffersapp.happypath;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;
import pl.luczak.michal.joboffersapp.JWTResponseDTO;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferSchedulerPort;
import pl.luczak.michal.joboffersapp.utils.SamplesOffersResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TypicalScenarioTest extends AbstractIntegrationTest implements SamplesOffersResponse {

    @DynamicPropertySource
    static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("job-offers.offer.fetcher.port", wireMockServer::getPort);
    }

    private static final Pattern jwtRegex = Pattern.compile("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$");

    @Autowired
    private OfferSchedulerPort offerScheduler;

    /*
        starter context:
            there are no offers in external HTTP server (http://ec2-3-120-147-150.eu-central-1.compute.amazonaws.com:5057/offers)

        step 1: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        step 2: user made GET /offers with no jwt token and system returned OK(200) with 0 offers
        step 3: user made POST /offers with offer and without token. Then system returned UNAUTHORIZED(401)
        step 4: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status CREATED(201)
        step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwtToken=AAAA.BBBB.CCC
        step 7: there are 2 new offers in external HTTP server
        step 8: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: ${firstOfferUUID} and ${secondOfferUUID} to database
        step 9: user made GET /offers system returned OK(200) with 2 offers
        step 10: user made GET /offers/${randomUUID} and system returned NOT_FOUND(404) with message “Offer with id ${randomUUID} not found”
        step 11: user made GET /offers/${firstOfferUUID} and system returned OK(200) with offer
        step 12: there are 2 new offers in external HTTP server
        step 13: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: ${thirdOfferUUID} and ${fourthOfferUUID} to database
        step 14: user made GET /offers and system returned OK(200) with 4 offers with ids: ${firstOfferUUID}, ${secondOfferUUID}, ${thirdOfferUUID} and ${fourthOfferUUID}
        step 15: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer and system returned CREATED(201) with saved offer
        step 16: user made GET /offers and system returned OK(200) with 5 offers
    */


    @Test
    void typical_scenario() throws Exception {
    //step 1: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        // GIVEN
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        // WHEN
        List<OfferDTO> emptyOfferList = offerScheduler.fetchOffers();

        // THEN
        assertThat(emptyOfferList).isEmpty();

    //step 2: user made GET /offers with no jwt token and system returned OK(200) with 0 offers
        // GIVEN && WHEN
        ResultActions getResultActions = mockMvc.perform(get("/offers"));
        String stringFromGetResultActions = getResultActions.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // THEN
        assertThat(stringFromGetResultActions).isEqualTo("[]");

    //step 3: user made POST /offers with offer and without token. Then system returned UNAUTHORIZED(401)
        // GIVEN && WHEN
        ResultActions postResultActions = mockMvc.perform(post("/offers")
                .content(oneOffer())
                .contentType(MediaType.APPLICATION_JSON)
        );

        // THEN
        postResultActions.andExpect(status().isUnauthorized());

    // step 4: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        // GIVEN && WHEN
        ResultActions postTokenResultActions = mockMvc.perform(post("/token")
                .content("""
                        {
                            "username" : "username",
                            "password" : "password"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // THEN
        postTokenResultActions.andExpect(status().isUnauthorized());

    //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status CREATED(201)
        // GIVEN && WHEN
        ResultActions registerRequestOK = mockMvc.perform(post("/register")
                .content("""
                        {
                            "username": "username",
                            "password": "password"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        int status = registerRequestOK.andReturn()
                .getResponse()
                .getStatus();

        // THEN
        assertThat(status).isEqualTo(201);

    //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // GIVEN && WHEN
        ResultActions tokenPostOk = mockMvc.perform(post("/token")
                .content("""
                        {
                            "username": "username",
                            "password": "password"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
        );
        String tokenResponse = tokenPostOk.andReturn()
                .getResponse()
                .getContentAsString();
        JWTResponseDTO jwtResponseDTO = objectMapper.readValue(tokenResponse, JWTResponseDTO.class);
        int statusOfTokenPostOk = tokenPostOk.andReturn()
                .getResponse()
                .getStatus();

        // THEN
        assertThat(statusOfTokenPostOk).isEqualTo(200);
        assertThat(jwtResponseDTO.token()).matches(jwtRegex);

    //step 7: there are 2 new offers in external HTTP server
        // GIVEN && WHEN && THEN
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(twoOffers())));

    //step 8: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: ${firstOfferUUID} and ${secondOfferUUID} to database
        // GIVEN && WHEN
        List<OfferDTO> offerDTOList = offerScheduler.fetchOffers();
        OfferDTO firstOffer = offerDTOList.get(0);
        OfferDTO secondOffer = offerDTOList.get(1);
        UUID firstOfferUUID = firstOffer.uniqueID();
        UUID secondOfferUUID = secondOffer.uniqueID();

        // THEN
        assertThat(offerDTOList).hasSize(2);

    //step 9: user made GET /offer system returned OK(200) with 2 offers
        // GIVEN && WHEN
        ResultActions getResultActionsWithTwoOffers = mockMvc.perform(get("/offers"));
        String stringFromGetResultActionsWithTwoOffers = getResultActionsWithTwoOffers.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        List<OfferDTO> twoOffersList = objectMapper.readValue(
                stringFromGetResultActionsWithTwoOffers,
                new TypeReference<>(){}
        );

        // THEN
        assertThat(twoOffersList).containsExactlyInAnyOrderElementsOf(offerDTOList);

    //step 10: user made GET /offer/${randomUUID} and system returned NOT_FOUND(404) with message “Offer with id ${randomUUID} not found”
        // GIVEN
        UUID randomUUID = UUID.randomUUID();

        // WHEN
        ResultActions getWithRandomUUID = mockMvc.perform(get("/offers/" + randomUUID));
        String notFoundResponse = getWithRandomUUID.andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        // THEN
        assertThat(notFoundResponse).contains("Offer with uniqueID: " + randomUUID);

    //step 11: user made GET /offers/${firstOfferUUID} and system returned OK(200) with offer
        // GIVEN && WHEN
        ResultActions getWithFirstOfferUUID = mockMvc.perform(get("/offers/" + firstOfferUUID));
        String okResponse = getWithFirstOfferUUID.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        OfferDTO firstFoundOfferDTO = objectMapper.readValue(okResponse, OfferDTO.class);

        // THEN
        assertThat(firstFoundOfferDTO).isEqualTo(firstOffer);

    //step 12: there are 2 new offers in external HTTP server
        // GIVEN && WHEN && THEN
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(twoOtherOffers())));

    //step 13: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: ${thirdOfferUUID} and ${fourthOfferUUID} to database
        // GIVEN && WHEN
        List<OfferDTO> offerListWith2NewOffers = offerScheduler.fetchOffers();
        OfferDTO thirdOffer = offerListWith2NewOffers.get(0);
        OfferDTO fourthOffer = offerListWith2NewOffers.get(1);
        UUID thirdOfferUUID = thirdOffer.uniqueID();
        UUID fourthOfferUUID = fourthOffer.uniqueID();

        // THEN
        assertThat(offerListWith2NewOffers).hasSize(2);

    //step 14: user made GET /offers and system returned OK(200) with 4 offers with ids: ${firstOfferUUID}, ${secondOfferUUID}, ${thirdOfferUUID} and ${fourthOfferUUID}
        // GIVEN && WHEN
        ResultActions getWithFourOffers = mockMvc.perform(get("/offers"));
        String getWithFourOffersResponse = getWithFourOffers.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        List<OfferDTO> fourOfferList = objectMapper.readValue(getWithFourOffersResponse, new TypeReference<>(){});

        // THEN
        assertThat(fourOfferList).containsExactlyInAnyOrder(firstOffer, secondOffer, thirdOffer, fourthOffer);

    //step 15: user made POST /offers with header “Authorization: Bearer AAAA.BBBB.CCC” and offer and system returned CREATED(201) with saved offer
        // GIVEN && WHEN
        ResultActions offerPostCreated = mockMvc.perform(post("/offers")
                .content("""
                        {
                            "title": "Junior Java Developer",
                            "company": "BlueSoft Sp. z o.o.",
                            "salary": "7 000 – 9 000 PLN",
                            "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-bluesoft-remote-hfuanrre"
                        }
                        """)
                .header("Authorization", "Bearer " + jwtResponseDTO.token())
                .contentType(MediaType.APPLICATION_JSON)
        );
        int statusOfOfferPostCreated = offerPostCreated.andReturn()
                .getResponse()
                .getStatus();

        // THEN
        assertThat(statusOfOfferPostCreated).isEqualTo(201);

    //step 16: user made GET /offers and system returned OK(200) with 5 offers
        // GIVEN && WHEN
        ResultActions getWithFiveOffers = mockMvc.perform(get("/offers"));
        String getWithFiveOffersResponse = getWithFiveOffers.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        List<OfferDTO> fiveOfferList = objectMapper.readValue(getWithFiveOffersResponse, new TypeReference<>(){});
        int statusOfGetWithFiveOffers = getWithFiveOffers.andReturn()
                .getResponse()
                .getStatus();

        // THEN
        assertThat(statusOfGetWithFiveOffers).isEqualTo(200);
        assertThat(fiveOfferList).hasSize(5);
    }
}
