package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class UnauthorizedDataAccessExceptionTest {
    @Test
    public void testUnauthorizedDataAccessException() {
        UnauthorizedDataAccessException exception = new UnauthorizedDataAccessException();
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("ID_NOT_FROM_LOGGED_IN_USER", exception.getReason());
    }
}