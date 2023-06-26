package pl.luczak.michal.joboffersapp.error;

import java.util.List;

public record BadCredentialsResponseDTO(List<String> errors) {
}
