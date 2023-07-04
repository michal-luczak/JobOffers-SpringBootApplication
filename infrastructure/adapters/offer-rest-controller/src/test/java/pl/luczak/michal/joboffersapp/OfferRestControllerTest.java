package pl.luczak.michal.joboffersapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;
import pl.luczak.michal.joboffersapp.ports.output.OfferService;
import pl.luczak.michal.joboffersapp.utils.SamplesOffersResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = OfferRestController.class)
@ContextConfiguration(classes = OfferControllerTestConfig.class)
@AutoConfigureMockMvc
class OfferRestControllerTest implements SamplesOffersResponse {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferDTO exemplaryOffer;

    @Test
    void should_successfully_saveOffer() throws Exception {
        OfferSaveRequest offerSaveRequest = OfferSaveRequest.builder()
                .url("testUrl")
                .jobName("testJobName")
                .salary("testSalary")
                .companyName("testCompanyName")
                .build();
        ResultActions resultActions = mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(offerSaveRequest)));
        String responseContent = resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        UUID uniqueID = UUID.fromString(responseContent);
        verify(offerService, times(1)).saveOffer(Mockito.any());
        assertThat(exemplaryOffer.uniqueID()).isEqualTo(uniqueID);
    }

    @Test
    void should_successfully_findAllOffers() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/offers"));
        List<OfferDTO> offerDTOList = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<>() {}
        );
        List<OfferDTO> offerListWithUUID = addUniqueIDToList(offerDTOList);
        List<OfferDTO> offerList = addUniqueIDToList(threeOfferDTO());
        verify(offerService, times(1)).findAllOffers();
        assertThat(offerListWithUUID)
                .usingRecursiveComparison()
                .ignoringFields("uniqueID")
                .isEqualTo(offerList);
    }

    @Test
    void should_successfully_findOfferById() throws Exception {
        UUID uniqueID = exemplaryOffer.uniqueID();
        ResultActions resultActions = mockMvc.perform(get("/offers/" + uniqueID));
        OfferDTO foundOfferDTO = objectMapper.readValue(
                resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
                new TypeReference<>() {}
        );
        verify(offerService, times(1)).findOfferById(Mockito.any());
        assertThat(foundOfferDTO).usingRecursiveComparison()
                .ignoringFields("uniqueID")
                .isEqualTo(exemplaryOffer);
    }
}