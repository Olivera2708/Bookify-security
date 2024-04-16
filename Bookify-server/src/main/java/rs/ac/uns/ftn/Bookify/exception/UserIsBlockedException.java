package rs.ac.uns.ftn.Bookify.exception;

public class UserIsBlockedException extends RuntimeException{
    public UserIsBlockedException(){
        super("Account is currently blocked");
    }
}
