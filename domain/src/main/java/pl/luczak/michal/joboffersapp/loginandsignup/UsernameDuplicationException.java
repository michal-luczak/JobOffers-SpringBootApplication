package pl.luczak.michal.joboffersapp.loginandsignup;

public class UsernameDuplicationException extends RuntimeException {
    public UsernameDuplicationException(String message) {
        super(message);
    }
}
