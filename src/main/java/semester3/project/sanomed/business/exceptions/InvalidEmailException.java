package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidEmailException extends ResponseStatusException {

    public InvalidEmailException() {
        super(HttpStatus.BAD_REQUEST, "This email is already taken");
    }

}
