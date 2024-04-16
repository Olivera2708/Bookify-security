package rs.ac.uns.ftn.Bookify.exception;

public class UserDeletionException extends RuntimeException{
    public UserDeletionException(){}
    public UserDeletionException(String reason) {super(reason);}
}
