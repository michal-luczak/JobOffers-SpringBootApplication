package pl.luczak.michal.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "offers")
record OfferDocument(
    @Id
    String uniqueID,
    String url,
    String companyName,
    String jobName,
    String salary
) {
        OfferDocument.OfferDocumentBuilder toBuilder() {
                return OfferDocument.builder()
                        .uniqueID(uniqueID)
                        .companyName(companyName)
                        .jobName(jobName)
                        .url(url)
                        .salary(salary);
        }

        boolean isNew() {
                return uniqueID == null;
        }
}