package semester3.project.sanomed.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidAppointmentException extends ResponseStatusException {

    public InvalidAppointmentException () {
        super(HttpStatus.NOT_FOUND, "Invalid ID, does not exist");
    }
}
