package pl.luczak.michal.joboffersapp.loginandsignup;

import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.io.Serializable;
import java.util.function.Function;

public interface IUserDTOMapper<T> extends Function<T, UserDTO>, Serializable {

    default UserDTO toUserDTO(T t) {
        return apply(t);
    };

    T fromUserDTO(UserDTO offerDTO);
}
