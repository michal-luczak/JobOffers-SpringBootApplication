package pl.luczak.michal.joboffersapp.offer;

import com.mongodb.DuplicateKeyException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ContextConfiguration;
import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


/* TODO change embed mongodb lib for test units
    * This implementation doesn't throw DuplicateKeyException
    * and overwrites objects when id is the same
*/
@Disabled
@DataMongoTest
@ContextConfiguration(classes = OfferRepositoryTestConfig.class)
class OfferRepositoryTest {

    @Autowired
    private OfferRepository offerRepository;

    @AfterEach
    void tearDown() {
        offerRepository.deleteAll();
    }

    @Test
    void should_successfully_save_offer() {
        // GIVEN
        String url = "testUrl";
        OfferDocument offerDocument = OfferDocument.builder()
                .url(url)
                .companyName("testCompanyName")
                .jobName("testJobName")
                .salary("testSalary")
                .uniqueID(null)
                .build();

        // WHEN
        offerRepository.save(offerDocument);
        Optional<OfferDocument> document = offerRepository.findByUrl(url);

        // THEN
        assertAll(() -> {
            assertThat(document).isPresent();
            assertThat(document.get()).usingRecursiveComparison()
                    .ignoringFields("uniqueID")
                    .isEqualTo(offerDocument);
        });
    }

    @Test
    void should_throw_an_exception_because_of_duplication() {
        // GIVEN
        String url = "testUrl";
        OfferDocument offerDocument = OfferDocument.builder()
                .url(url)
                .companyName("testCompanyName")
                .jobName("testJobName")
                .salary("testSalary")
                .uniqueID(null)
                .build();

        // WHEN
        offerRepository.save(offerDocument);
        offerRepository.save(offerDocument);

        // THEN
        assertThrows(
                DuplicateKeyException.class,
                () -> offerRepository.save(offerDocument)
        );
    }

    @Test
    void should_successfully_find_offer_by_url() {
        // GIVEN
        String url = "testUrl";
        OfferDocument offerDocument = OfferDocument.builder()
                .url(url)
                .companyName("testCompanyName")
                .jobName("testJobName")
                .salary("testSalary")
                .uniqueID(null)
                .build();

        // WHEN
        offerRepository.save(offerDocument);
        Optional<OfferDocument> document = offerRepository.findByUrl(url);

        // THEN
        assertAll(() -> {
            assertThat(document).isPresent();
            assertThat(document.get())
                    .usingRecursiveComparison()
                    .ignoringFields("uniqueID")
                    .isEqualTo(offerDocument);
        });
    }

    @Test
    void should_not_find_offer() {
        // GIVEN
        String url = "testUrl";

        // WHEN
        Optional<OfferDocument> document = offerRepository.findByUrl(url);

        // THEN
        assertThat(document).isEmpty();
    }
}