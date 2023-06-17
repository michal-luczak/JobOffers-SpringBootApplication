package pl.luczak.michal.joboffersapp.loginandsignup;

public class UserIdDuplicationException extends RuntimeException {

    public UserIdDuplicationException(String message) {
        super(message);
    }
}
