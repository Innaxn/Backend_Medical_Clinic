package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidBsnException extends ResponseStatusException {
    public InvalidBsnException() {
        super(HttpStatus.BAD_REQUEST, "The bsn is already used by another user");
    }
}
