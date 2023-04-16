package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordException extends ResponseStatusException {

    public InvalidPasswordException() {
        super(HttpStatus.BAD_REQUEST, "The input of our old password does not match!");
    }
}
