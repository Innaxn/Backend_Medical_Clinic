package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidPatientExceptionTest {
    @Test
    public void testInvalidPatientException() {
        InvalidPatientException exception = new InvalidPatientException();
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("INVALID_PATIENT_ID", exception.getReason());
    }
}