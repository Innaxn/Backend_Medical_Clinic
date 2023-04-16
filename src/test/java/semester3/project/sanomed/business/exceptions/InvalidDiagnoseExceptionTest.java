package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidDiagnoseExceptionTest {
    @Test
    public void testInvalidDiagnoseException() {
        InvalidDiagnoseException exception = new InvalidDiagnoseException();
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("INVALID_DIAGNOSE_ID", exception.getReason());
    }
}