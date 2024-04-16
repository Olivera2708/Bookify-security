package rs.ac.uns.ftn.Bookify.exception;

public class UserNotActivatedException extends RuntimeException {

    public UserNotActivatedException() {super("Account is not activated please check your email");}
}
