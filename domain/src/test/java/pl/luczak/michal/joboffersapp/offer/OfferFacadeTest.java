package pl.luczak.michal.joboffersapp.offer;

import org.junit.jupiter.api.Test;
import pl.luczak.michal.joboffersapp.ports.input.offer.OfferDAOPort;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.utils.SamplesOffersResponse;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest implements SamplesOffersResponse {

    private final OfferFetcherAdapter offerFetcherAdapter = new OfferFetcherAdapter();
    private final OfferDAOPort offerPersistenceAdapter = new InMemoryOfferPersistenceAdapter();
    private final OfferFacade<OfferFetchedDTO> offerFacade = new OfferFacade<>(
            offerPersistenceAdapter,
            offerFetcherAdapter
    );

    @Test
    void should_return_all_offers() {
        //given
        List<OfferDTO> randomOfferDTOS = threeOfferDTO();
        offerPersistenceAdapter.saveAllOffers(randomOfferDTOS);

        //when
        List<OfferDTO> foundOffers = offerFacade.findAllOffers();

        //then
        assertThat(foundOffers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("uniqueID")
                .containsExactlyInAnyOrderElementsOf(randomOfferDTOS);
    }

    @Test
    void should_successfully_save_offer() {
        //given
        OfferDTO offerDTO = oneOfferDTO();

        //when
        UUID savedOfferUUID = offerFacade.saveOffer(offerDTO);
        OfferDTO foundOfferDTO = offerPersistenceAdapter.findOfferById(savedOfferUUID)
                .orElseThrow();

        //then
        assertEquals(0,
                new OfferComparator().compare(
                        offerDTO,
                        foundOfferDTO
                ));
    }

    @Test
    void should_successfully_find_offer_by_id() {
        //given
        OfferDTO offerDTO = oneOfferDTO();

        //when
        UUID uniqueID = offerPersistenceAdapter.saveOffer(offerDTO);
        OfferDTO foundOffer = offerFacade.findOfferById(uniqueID);

        //then
        assertEquals(0,
                new OfferComparator().compare(
                        offerDTO,
                        foundOffer
                ));
    }

    @Test
    void should_unsuccessfully_find_offer_by_id_and_throw_OfferNotFoundException() {
        //then
        assertThrows(
                OfferNotFoundException.class,
                () -> offerFacade.findOfferById(UUID.randomUUID())
        );
    }

    @Test
    void should_successfully_fetch_offers_from_fetcher_and_save_all_offers_which_not_exists() {
        //given
        List<OfferDTO> randomOffers = threeOfferDTO();
        List<OfferFetchedDTO> randomOfferRequests = randomOffers.stream()
                        .map(offerDTO -> OfferFetchedDTO.builder()
                                .url(offerDTO.url())
                                .salary(offerDTO.salary())
                                .jobName(offerDTO.jobName())
                                .companyName(offerDTO.companyName())
                                .build())
                        .toList();
        randomOfferRequests.forEach(offerFetcherAdapter::addOffer);

        //when
        List<OfferDTO> foundOffer = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        //then
        assertThat(foundOffer).usingRecursiveFieldByFieldElementComparatorIgnoringFields("uniqueID")
                .containsExactlyInAnyOrderElementsOf(randomOffers);
    }
}