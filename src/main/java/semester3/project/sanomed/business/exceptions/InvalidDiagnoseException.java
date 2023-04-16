package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidDiagnoseException extends ResponseStatusException {

    public InvalidDiagnoseException() {
        super(HttpStatus.NOT_FOUND, "INVALID_DIAGNOSE_ID");
    }

}
