package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidBsnExceptionTest {

    @Test
    public void testInvalidBsnException() {
        InvalidBsnException exception = new InvalidBsnException();
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The bsn is already used by another user", exception.getReason());
    }
}