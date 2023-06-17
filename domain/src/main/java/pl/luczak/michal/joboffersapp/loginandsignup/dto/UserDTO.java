package pl.luczak.michal.joboffersapp.loginandsignup.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record UserDTO(Long id, String username, String password) {

}
