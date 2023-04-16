package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidPrescriptionExceptionTest {
    @Test
    public void testInvalidPrescriptionException() {
        InvalidPrescriptionException exception = new InvalidPrescriptionException();
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("INVALID_PRESCRIPTION_ID", exception.getReason());
    }
}