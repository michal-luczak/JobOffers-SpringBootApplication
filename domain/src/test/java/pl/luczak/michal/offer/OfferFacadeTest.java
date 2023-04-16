package pl.luczak.michal.offer;

import org.junit.jupiter.api.Test;
import pl.luczak.michal.offer.dto.OfferDTO;
import pl.luczak.michal.ports.OfferPersistencePort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest {

    private final OfferFetcherAdapter offerFetcherAdapter = new OfferFetcherAdapter();
    private final OfferPersistencePort offerPersistenceAdapter = new InMemoryOfferPersistenceAdapter();
    private final OfferFacade offerFacade = new OfferFacade(
            offerPersistenceAdapter,
            offerFetcherAdapter
    );

    @Test
    void should_return_all_offers() {
        //given
        List<OfferDTO> randomOfferDTOS = generateRandomOffers();
        offerPersistenceAdapter.saveAllOffers(randomOfferDTOS);

        //when
        List<OfferDTO> foundOffers = offerFacade.findAllOffers();

        //then
        assertTrue(
                foundOffers.containsAll(randomOfferDTOS)
                        && randomOfferDTOS.containsAll(foundOffers)
        );
    }

    @Test
    void should_successfully_save_offer() {
        //given
        OfferDTO offerDTO = generateRandomOffers().get(0);

        //when
        UUID savedOfferUUID = offerFacade.saveOffer(offerDTO);
        OfferDTO optionalOfferDTO = offerPersistenceAdapter.findOfferById(offerDTO.uniqueID())
                .orElseThrow();

        //then
        assertEquals(offerDTO, optionalOfferDTO);
    }

    @Test
    void should_successfully_find_offer_by_id() {
        //given
        OfferDTO offerDTO = generateRandomOffers().get(0);

        //when
        offerPersistenceAdapter.saveOffer(offerDTO);
        OfferDTO foundOffer = offerFacade.findOfferById(offerDTO.uniqueID());

        //then
        assertEquals(offerDTO, foundOffer);
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
        List<OfferDTO> randomOffers = generateRandomOffers();
        randomOffers.forEach(offerFetcherAdapter::addOffer);

        //when
        List<OfferDTO> foundOffer = offerFacade.fetchAllOffersAndSaveAllIfNotExists();

        //then
        assertEquals(randomOffers, foundOffer);
        assertTrue(
                randomOffers.containsAll(offerPersistenceAdapter.findAllOffers())
                        && offerPersistenceAdapter.findAllOffers().containsAll(randomOffers)
        );
    }

    private static List<OfferDTO> generateRandomOffers() {
        List<OfferDTO> offerDTOList = new ArrayList<>();
        Random random = new Random();
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        for (int i = 0; i < 5; i++) {
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int j = 0; j < targetStringLength; j++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            OfferDTO offerDTO = OfferDTO.builder()
                    .uniqueID(UUID.randomUUID())
                    .url(generatedString)
                    .offerDescription(generatedString)
                    .jobName(generatedString)
                    .companyName(generatedString)
                    .salary(generatedString)
                    .build();
            offerDTOList.add(offerDTO);
        }
        return offerDTOList;
    }
}