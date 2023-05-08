package pl.luczak.michal.joboffersapp.loginandsignup.dto;

import lombok.Builder;

@Builder
public record RegistrationRequestDTO(String username, String password) {
}
