package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidHealthInsNumExceptionTest {
    @Test
    public void testInvalidHealthInsNumException() {
        InvalidHealthInsNumException exception = new InvalidHealthInsNumException();
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The health insurance number is already used by another user", exception.getReason());
    }
}