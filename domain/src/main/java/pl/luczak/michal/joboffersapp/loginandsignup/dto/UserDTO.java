package pl.luczak.michal.joboffersapp.loginandsignup.dto;

import lombok.Builder;

@Builder
public record UserDTO(Long id, String username, String password) {

}
