package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAccessTokenException  extends ResponseStatusException {
    public InvalidAccessTokenException(String errorCause) {
        super(HttpStatus.UNAUTHORIZED, errorCause);
    }

}
