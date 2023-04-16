package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPatientException extends ResponseStatusException {

    public InvalidPatientException() {
        super(HttpStatus.NOT_FOUND, "INVALID_PATIENT_ID");
    }
}
