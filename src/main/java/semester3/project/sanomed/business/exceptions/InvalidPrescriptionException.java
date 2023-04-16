package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPrescriptionException extends ResponseStatusException {
    public InvalidPrescriptionException() {
        super(HttpStatus.NOT_FOUND, "INVALID_PRESCRIPTION_ID");
    }

}
