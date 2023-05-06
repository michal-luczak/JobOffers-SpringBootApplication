package pl.luczak.michal.offer;

import pl.luczak.michal.offer.dto.OfferDTO;

import java.io.Serializable;
import java.util.function.Function;

public interface IOfferDTOMapper<T> extends Function<T, OfferDTO>, Serializable {

    OfferDTO toOfferDTO(T t);

    T fromOfferDTO(OfferDTO offerDTO);
}
