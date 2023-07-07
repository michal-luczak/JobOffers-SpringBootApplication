package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;
import pl.luczak.michal.joboffersapp.utils.SamplesOffersResponse;

import java.util.List;
import java.util.UUID;

@Configuration
abstract class OfferControllerTestConfig implements SamplesOffersResponse {

    @Bean
    OfferRestController offerRestController(
            OfferSaveRequestToOfferDTOMapper offerSaveRequestToOfferDTOMapper,
            OfferService offerService,
            CacheableFacade cacheableFacade
    ) {
        return new OfferRestController(offerService, offerSaveRequestToOfferDTOMapper, cacheableFacade);
    }

    @Bean
    OfferSaveRequestToOfferDTOMapper offerSaveRequestToOfferDTOMapper() {
        return new OfferSaveRequestToOfferDTOMapper();
    }

    @Bean
    UUID uniqueID() {
        return UUID.randomUUID();
    }

    @Bean
    OfferDTO offerDTO(UUID uniqueID) {
        return oneOfferDTO().toBuilder()
                .uniqueID(uniqueID)
                .build();
    }

    @Bean
    OfferService offerService(OfferDTO offerDTO) {
        OfferService offerServiceMock = Mockito.mock(OfferService.class);
        List<OfferDTO> threeOfferDTOList = addUniqueIDToList(threeOfferDTO());
        Mockito.when(offerServiceMock.findAllOffers()).thenReturn(threeOfferDTOList);
        Mockito.when(offerServiceMock.findOfferById(offerDTO.uniqueID())).thenReturn(offerDTO);
        Mockito.when(offerServiceMock.saveOffer(Mockito.any())).thenReturn(offerDTO.uniqueID());
        return offerServiceMock;
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
