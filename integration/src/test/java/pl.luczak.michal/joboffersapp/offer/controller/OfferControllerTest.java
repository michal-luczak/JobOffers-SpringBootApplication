package pl.luczak.michal.joboffersapp.offer.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import pl.luczak.michal.joboffersapp.AbstractIntegrationTest;
import pl.luczak.michal.joboffersapp.dto.OfferRequestDTO;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferDAOPort;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;
import pl.luczak.michal.joboffersapp.utils.SamplesOffersResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class OfferControllerTest extends AbstractIntegrationTest implements SamplesOffersResponse {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferDAOPort offerDAO;

    @DynamicPropertySource
    protected static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("job-offers.offer.fetcher.port", wireMockServer::getPort);
    }

    @AfterEach
    void clearDatabase() {
        offerDAO.findAllOffers()
                .forEach(offerDTO -> offerDAO.deleteOffer(offerDTO));
    }

    @Test
    @WithMockUser
    void should_successfully_save_offer_to_database() throws Exception {
        // GIVEN && WHEN
        ResultActions resultActions = mockMvc.perform(
                post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(oneOffer().trim())
        );
        String resultAsString = resultActions.andReturn()
                .getResponse()
                .getContentAsString();
        OfferDTO foundOffer = offerService.findOfferById(UUID.fromString(resultAsString));
        OfferRequestDTO offerRequestDTO = objectMapper.readValue(oneOffer(), OfferRequestDTO.class);

        // THEN
        assertThat(
                resultActions.andReturn()
                        .getResponse()
                        .getStatus()
        ).isEqualTo(201);
        assertThat(foundOffer).isNotNull();
        assertThat(foundOffer).usingRecursiveComparison()
                .ignoringFields("uniqueID")
                .isEqualTo(offerRequestDTO.toOfferDTO());
    }

    @Test
    @WithMockUser
    void should_return_CONFLICT_caused_by_offer_duplication() throws Exception {
        // GIVEN
        OfferDTO offerDTO = objectMapper.readValue(oneOffer(), OfferRequestDTO.class).toOfferDTO();

        // WHEN
        offerService.saveOffer(offerDTO);
        ResultActions resultActions = mockMvc.perform(
                post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(oneOffer().trim())
        );

        // THEN
        assertThat(
                resultActions.andReturn()
                        .getResponse()
                        .getStatus()
        ).isEqualTo(409);
    }

    @Test
    void should_return_NOT_FOUND_caused_by_nonexistent_offer() throws Exception {
        // GIVEN
        UUID uniqueID = UUID.randomUUID();

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                get("/offers/" + uniqueID)
        );

        // THEN
        assertThat(
                resultActions.andReturn()
                        .getResponse()
                        .getStatus()
        ).isEqualTo(404);
    }

    @Test
    void should_successfully_return_offer_with_specific_UUID() throws Exception {
        // GIVEN
        OfferDTO offerDTO = oneOfferDTO();

        // WHEN
        UUID uniqueIDOfSavedOffer = offerService.saveOffer(offerDTO);
        ResultActions resultActions = mockMvc.perform(
                get("/offers/" + uniqueIDOfSavedOffer)
        );
        String resultAsString = resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        OfferDTO foundOffer = objectMapper.readValue(resultAsString, OfferDTO.class);

        // THEN
        assertThat(
                resultActions.andReturn()
                        .getResponse()
                        .getStatus()
        ).isEqualTo(200);
        assertThat(foundOffer).isNotNull();
        assertThat(foundOffer).usingRecursiveComparison()
                .ignoringFields("uniqueID")
                .isEqualTo(offerDTO);
    }

    @Test
    void should_successfully_return_empty_array_because_of_empty_offer_database() throws Exception {
        // GIVEN && WHEN
        ResultActions resultActionWithEmptyDB = mockMvc.perform(
                get("/offers")
        );
        String contentWithEmptyDB = resultActionWithEmptyDB.andReturn()
                .getResponse()
                .getContentAsString();

        // THEN
        assertThat(
                resultActionWithEmptyDB.andReturn()
                        .getResponse()
                        .getStatus()
        ).isEqualTo(200);
        assertThat(contentWithEmptyDB).isEqualTo("[]");
    }

    @Test
    void should_successfully_return_all_offers_from_database() throws Exception {
        // GIVEN
        OfferDTO firstOfferDTO = oneOfferDTO();
        OfferDTO secondOfferDTO = oneOtherOfferDTO();

        // WHEN
        UUID uniqueIDOfFirstOffer = offerService.saveOffer(firstOfferDTO);
        UUID uniqueIDOfSecondOffer = offerService.saveOffer(secondOfferDTO);
        firstOfferDTO = firstOfferDTO.toBuilder()
                .uniqueID(uniqueIDOfFirstOffer)
                .build();
        secondOfferDTO = secondOfferDTO.toBuilder()
                .uniqueID(uniqueIDOfSecondOffer)
                .build();
        ResultActions resultAction = mockMvc.perform(
                get("/offers")
        );
        String content = resultAction.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        // THEN
        assertThat(
                resultAction.andReturn()
                        .getResponse()
                        .getStatus()
        ).isEqualTo(200);
        assertThat(content)
                .isEqualTo(
                    objectMapper.writeValueAsString(
                            List.of(firstOfferDTO, secondOfferDTO)
                    )
                );
    }
}
