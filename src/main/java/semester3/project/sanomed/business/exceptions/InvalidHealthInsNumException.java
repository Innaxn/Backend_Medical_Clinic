package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidHealthInsNumException extends ResponseStatusException {

    public InvalidHealthInsNumException() {
        super(HttpStatus.BAD_REQUEST, "The health insurance number is already used by another user");
    }
}
