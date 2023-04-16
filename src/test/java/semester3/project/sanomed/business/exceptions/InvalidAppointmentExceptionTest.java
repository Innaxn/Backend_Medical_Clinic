package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidAppointmentExceptionTest {
    @Test
    public void testInvalidAppointmentException() {
        InvalidAppointmentException exception = new InvalidAppointmentException();
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Invalid ID, does not exist", exception.getReason());
    }
}