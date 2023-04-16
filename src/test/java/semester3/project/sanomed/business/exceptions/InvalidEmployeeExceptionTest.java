package semester3.project.sanomed.business.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class InvalidEmployeeExceptionTest {
    @Test
    public void testInvalidEmployeeException() {
        InvalidEmployeeException exception = new InvalidEmployeeException();
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("INVALID_EMPLOYEE_ID", exception.getReason());
    }
}