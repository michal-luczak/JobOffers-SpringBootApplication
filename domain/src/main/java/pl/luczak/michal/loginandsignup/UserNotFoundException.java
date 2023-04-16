package pl.luczak.michal.loginandsignup;

class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "User with username: {username} not found";

    public UserNotFoundException(String username) {
        super(MESSAGE.replace("{username}", username));
    }
}
