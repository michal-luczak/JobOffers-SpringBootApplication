package pl.luczak.michal.joboffersapp.offer;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "offers")
record OfferDocument(
    @Id
    String uniqueID,
    @Indexed(unique = true)
    String url,
    String companyName,
    String jobName,
    String salary
) { }