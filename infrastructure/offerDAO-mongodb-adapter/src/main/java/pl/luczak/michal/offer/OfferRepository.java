package pl.luczak.michal.offer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface OfferRepository extends MongoRepository<OfferDocument, String> {

    Optional<OfferDocument> findByUrl(String url);
}
