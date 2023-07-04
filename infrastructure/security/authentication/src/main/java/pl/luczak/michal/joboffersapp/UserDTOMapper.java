package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import pl.luczak.michal.joboffersapp.loginandsignup.IUserDTOMapper;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

import java.util.Collections;

@AllArgsConstructor
class UserDTOMapper implements IUserDTOMapper<User> {

    @Override
    public User fromUserDTO(UserDTO offerDTO) {
        return new User(
                offerDTO.username(),
                offerDTO.password(),
                Collections.emptyList()
        );
    }

    @Override
    public UserDTO apply(User userDetailsDTO) {
        return UserDTO.builder()
                .username(userDetailsDTO.getUsername())
                .password(userDetailsDTO.getPassword())
                .build();
    }
}
