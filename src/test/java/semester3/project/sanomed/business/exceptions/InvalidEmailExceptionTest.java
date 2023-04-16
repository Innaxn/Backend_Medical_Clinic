package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidEmailExceptionTest {
    @Test
    public void testInvalidEmailException() {
        InvalidEmailException exception = new InvalidEmailException();
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("This email is already taken", exception.getReason());
    }
}