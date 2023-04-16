package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidCredentialsExceptionTest {
    @Test
    public void testInvalidCredentialsException() {
        InvalidCredentialsException exception = new InvalidCredentialsException();
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("INVALID_CREDENTIALS", exception.getReason());
    }
}