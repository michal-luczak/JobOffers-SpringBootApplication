package pl.luczak.michal.joboffersapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OfferSaveRequestToOfferDTOMapperTest {

    private OfferSaveRequestToOfferDTOMapper offerDTOMapper;
    private OfferSaveRequest offerSaveRequest;
    private OfferDTO offerDTO;

    @BeforeEach
    void setUp() {
        offerDTOMapper = new OfferSaveRequestToOfferDTOMapper();
        UUID uniqueID = UUID.randomUUID();
        String url = "testURL";
        String companyName = "testCompany";
        String jobName = "testJobName";
        String salary = "testSalary";
        offerDTO = OfferDTO.builder()
                .uniqueID(uniqueID)
                .url(url)
                .companyName(companyName)
                .jobName(jobName)
                .salary(salary)
                .build();
        offerSaveRequest = OfferSaveRequest.builder()
                .companyName(companyName)
                .jobName(jobName)
                .salary(salary)
                .url(url)
                .build();
    }

    @Test
    void should_map_OfferDTO_to_OfferSaveRequest() {
        // GIVEN && WHEN
        OfferSaveRequest fromOfferDTO = offerDTOMapper.fromOfferDTO(offerDTO);

        // THEN
        assertThat(fromOfferDTO).isEqualTo(offerSaveRequest);
    }

    @Test
    void should_map_OfferSaveRequest_to_OfferDTO() {
        // GIVEN && WHEN
        OfferDTO applied = offerDTOMapper.apply(offerSaveRequest);

        // THEN
        assertThat(applied)
                .usingRecursiveComparison()
                .ignoringFields("uniqueID")
                .isEqualTo(offerDTO);
    }
}