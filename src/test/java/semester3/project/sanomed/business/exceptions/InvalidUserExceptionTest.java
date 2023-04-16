package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidUserExceptionTest {
    @Test
    public void testInvalidUserException() {
        InvalidUserException exception = new InvalidUserException();
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("INVALID_USER", exception.getReason());
    }
}