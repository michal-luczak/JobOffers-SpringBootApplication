package pl.luczak.michal.loginandsignup.dto;

import lombok.Builder;

@Builder
public record RegistrationRequestDTO(String username, String password) {
}
