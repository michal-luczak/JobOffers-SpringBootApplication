package pl.luczak.michal.joboffersapp.loginandsignup;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User with username: %s not found";

    public UserNotFoundException(String username) {
        super(MESSAGE.formatted(username));
    }
}
