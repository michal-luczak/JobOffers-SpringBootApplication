package pl.luczak.michal.loginandsignup.dto;

import lombok.Builder;

@Builder
public record UserDTO(Long id, String username, String password) {

}
