package pl.luczak.michal.joboffersapp.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.utils.SamplesOffersResponse;


import static org.assertj.core.api.Assertions.assertThat;

class OfferDTOMapperTest implements SamplesOffersResponse {

    private OfferDTOMapper offerDTOMapper;
    private OfferDTO offerDTO;
    private OfferDocument offerDocument;

    @BeforeEach
    void setUp() {
        this.offerDTOMapper = new OfferDTOMapper();
        this.offerDTO = oneOfferDTO();
        this.offerDocument = OfferDocument.builder()
                .uniqueID(null)
                .url("https://nofluffjobs.com/pl/job/junior-java-developer-convista-poland-wroclaw-akfbyrfk")
                .companyName("Efigence SA")
                .salary("7 500 – 11 500 PLN")
                .jobName("Junior Java Developer")
                .build();
    }

    @Test
    void should_map_OfferDTO_to_OfferDocument() {
        assertThat(offerDTOMapper.fromOfferDTO(offerDTO))
                .usingRecursiveComparison()
                .ignoringFields("uniqueID")
                .isEqualTo(offerDocument);
    }

    @Test
    void should_map_OfferDocument_to_OfferDTO() {
        assertThat(offerDTOMapper.apply(offerDocument))
                .usingRecursiveComparison()
                .ignoringFields("uniqueID")
                .isEqualTo(offerDTO);
    }
}