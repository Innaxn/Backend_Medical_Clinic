package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidAccessTokenExceptionTest {

    @Test
    public void testInvalidAccessTokenException() {
        InvalidAccessTokenException exception = new InvalidAccessTokenException("THIS USER IS UNAUTHORIZED");
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
        assertEquals("THIS USER IS UNAUTHORIZED", exception.getReason());
    }
}