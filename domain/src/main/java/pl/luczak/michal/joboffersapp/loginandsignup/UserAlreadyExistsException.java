package pl.luczak.michal.joboffersapp.loginandsignup;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
