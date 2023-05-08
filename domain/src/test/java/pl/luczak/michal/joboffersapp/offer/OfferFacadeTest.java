package pl.luczak.michal.joboffersapp.offer;

import org.junit.jupiter.api.Test;
import pl.luczak.michal.joboffersapp.ports.input.OfferDAOPort;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest {

    private final OfferFetcherAdapter offerFetcherAdapter = new OfferFetcherAdapter();
    private final OfferDAOPort offerPersistenceAdapter = new InMemoryOfferPersistenceAdapter();
    private final OfferFacade<OfferFetchedDTO> offerFacade = new OfferFacade<>(
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
        OfferDTO foundOfferDTO = offerPersistenceAdapter.findOfferById(offerDTO.uniqueID())
                .orElseThrow();

        //then
        assertEquals(offerDTO, foundOfferDTO);
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
        Iterator<OfferDTO> iterator = foundOffer.iterator();
        randomOffers.forEach(offerDTO -> {
            assertEquals(0,
                    new OfferComparator().compare(
                            offerDTO,
                            iterator.next()
                    ));
        });
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
                    .jobName(generatedString)
                    .companyName(generatedString)
                    .salary(generatedString)
                    .build();
            offerDTOList.add(offerDTO);
        }
        return offerDTOList;
    }
}