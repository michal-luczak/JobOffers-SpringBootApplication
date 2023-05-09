package pl.luczak.michal.joboffersapp;

import lombok.AllArgsConstructor;
import pl.luczak.michal.joboffersapp.loginandsignup.IUserDTOMapper;
import pl.luczak.michal.joboffersapp.loginandsignup.dto.UserDTO;

@AllArgsConstructor
class UserDTOMapper implements IUserDTOMapper<UserDetailsDTO> {

    @Override
    public UserDetailsDTO fromUserDTO(UserDTO offerDTO) {
        return new UserDetailsDTO(
                offerDTO.username(),
                offerDTO.password()
        );
    }

    @Override
    public UserDTO apply(UserDetailsDTO userDetailsDTO) {
        return UserDTO.builder()
                .username(userDetailsDTO.username())
                .password(userDetailsDTO.password())
                .build();
    }
}
