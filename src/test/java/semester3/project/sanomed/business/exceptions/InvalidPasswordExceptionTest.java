package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidPasswordExceptionTest {
    @Test
    public void testInvalidPasswordException() {
        InvalidPasswordException exception = new InvalidPasswordException();
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The input of our old password does not match!", exception.getReason());
    }
}