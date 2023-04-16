package semester3.project.sanomed.business.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidBsnException;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeIdValidatorImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeIdValidatorImpl employeeIdValidator;

    @Test
    void validateId() {
        InvalidEmployeeException thrown = assertThrows(InvalidEmployeeException.class, () ->{
            employeeIdValidator.validateId(1L);
        }, "INVALID_EMPLOYEE_ID");

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());
    }

    @Test
    void emaployeeIdAlreadyInUse() {
        long id = 12;
        when(employeeRepository.existsById(id)).thenReturn(false);

        assertThrows(InvalidEmployeeException.class, () -> {
            employeeIdValidator.validateId(id);
        });

    }
    @Test
    void emaployeeIdNotInUse() {
        long id = 12;
        when(employeeRepository.existsById(id)).thenReturn(true);

        employeeIdValidator.validateId(id);

    }
}