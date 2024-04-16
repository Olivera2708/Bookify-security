package rs.ac.uns.ftn.Bookify.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(value = {BadRequestException.class, UserIsBlockedException.class,
            UserNotActivatedException.class, UserDeletionException.class})
    public ResponseEntity<String> badRequestException(RuntimeException exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
