package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidEmployeeException extends ResponseStatusException {

    public InvalidEmployeeException() {
        super(HttpStatus.NOT_FOUND, "INVALID_EMPLOYEE_ID");
    }
}
