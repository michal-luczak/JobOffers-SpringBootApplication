package pl.luczak.michal.joboffersapp.utils;

import pl.luczak.michal.joboffersapp.offer.dto.OfferDTO;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface SamplesOffersResponse {

    default String oneOffer() {
        return """
            {
                "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-backend-developer-enigma-soi-warszawa-ziaekkrf",
                "company": "Enigma SOI",
                "title": "Junior Java Backend Developer",
                "salary": "6 300 – 12 000 PLN"
            }
        """.trim();
    }

    default String twoOffers() {
        return """
              [
                    {
                        "title": "Remote Java Developer with PLM knowledge",
                        "company": "Centrum Percall Polska sp. z o.o.",
                        "salary": "8 000 – 14 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/remote-java-developer-with-plm-knowledge-centrum-percall-polska-uy1nl7s3"
                    },
                    {
                        "title": "Junior Java Developer",
                        "company": "Convista Poland",
                        "salary": "6 600 – 11 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-convista-poland-wroclaw-akfbyrfk"
                    }
              ]
              """.trim();
    }

    default List<OfferDTO> twoOfferDTOs() {
        return List.of(OfferDTO.builder()
                        .url("https://nofluffjobs.com/pl/job/junior-java-developer-convista-poland-wroclaw-akfbyrfk")
                        .companyName("Efigence SA")
                        .salary("7 200 – 11 500 PLN")
                        .jobName("Junior Java Developer")
                        .build(),
                OfferDTO.builder()
                        .url("https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc")
                        .companyName("Convista Poland")
                        .salary("12 500 – 14 500 PLN")
                        .jobName("Junior Java Back-end Developer")
                        .build());
    }

    default String twoOtherOffers() {
        return """
                [
                    {
                        "title": "Java (CMS) Developer",
                        "company": "Efigence SA",
                        "salary": "16 000 – 18 000 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh"
                    },
                    {
                        "title": "Junior Java Developer",
                        "company": "Sollers Consulting",
                        "salary": "7 500 – 11 500 PLN",
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc"
                    }
                ]
                """.trim();
    }

    default OfferDTO oneOfferDTO() {
        return OfferDTO.builder()
                .url("https://nofluffjobs.com/pl/job/junior-java-developer-convista-poland-wroclaw-akfbyrfk")
                .companyName("Efigence SA")
                .salary("7 500 – 11 500 PLN")
                .jobName("Junior Java Developer")
                .build();
    }

    default OfferDTO oneOtherOfferDTO() {
        return OfferDTO.builder()
                .url("https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh")
                .companyName("Centrum Percall Polska sp. z o.o.")
                .salary("4 500 – 13 500 PLN")
                .jobName("Java Developer")
                .build();
    }

    default List<OfferDTO> threeOfferDTO() {
        return Arrays.stream(new OfferDTO[]{
                OfferDTO.builder()
                    .url("https://nofluffjobs.com/pl/job/java-cms-developer-efigence-warszawa-b4qs8loh")
                    .companyName("Centrum Percall Polska sp. z o.o.")
                    .salary("4 500 – 13 500 PLN")
                    .jobName("Java Developer")
                    .build(),
                OfferDTO.builder()
                    .url("https://nofluffjobs.com/pl/job/junior-java-developer-convista-poland-wroclaw-akfbyrfk")
                    .companyName("Efigence SA")
                    .salary("7 200 – 11 500 PLN")
                    .jobName("Junior Java Developer")
                    .build(),
                OfferDTO.builder()
                    .url("https://nofluffjobs.com/pl/job/junior-java-developer-sollers-consulting-warszawa-s6et1ucc")
                    .companyName("Convista Poland")
                    .salary("12 500 – 14 500 PLN")
                    .jobName("Junior Java Back-end Developer")
                    .build()}).toList();
    }

    default List<OfferDTO> addUniqueIDToList(List<OfferDTO> list) {
        return list.stream()
                .map(o -> o.toBuilder()
                        .uniqueID(UUID.randomUUID())
                        .build()
                )
                .toList();
    }
}
