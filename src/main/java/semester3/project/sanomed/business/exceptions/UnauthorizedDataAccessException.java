package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedDataAccessException extends ResponseStatusException {
    public UnauthorizedDataAccessException() {
        super(HttpStatus.BAD_REQUEST, "ID_NOT_FROM_LOGGED_IN_USER");
    }
}
