package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.converter.EmployeeConverter;
import semester3.project.sanomed.business.converter.PersonConverter;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.impl.employee.GetEmployeeUseCaseImpl;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.persistence.Entity.*;
import semester3.project.sanomed.persistence.EmployeeRepository;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEmployeeUseCaseImplTest {
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    GetEmployeeUseCaseImpl getEmployeeUseCase;

    @Test
    void getEmployee() {
        PersonEmbeddable oneP = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com")
                .phoneNumber("0898939914").birthdate(LocalDate.of(1980,10,1)).build();

        EmployeeEntity empOneEntity = EmployeeEntity.builder().id(1L)
                .personEmbeddable(oneP)
                .description("description")
                .status(Status_Employee.ACTIVE)
                .build();
        when(employeeRepositoryMock.findById(1L)).thenReturn(Optional.of(empOneEntity));

        Employee actualResult = getEmployeeUseCase.getEmployee(1L);

        Employee expected = Employee.builder().id(1L)
                .person(PersonConverter.convert(oneP))
                .description("description")
                .status(Status_Employee.ACTIVE)
                .build();

        assertEquals(expected, actualResult);
        verify(employeeRepositoryMock).findById(1L);
    }

    @Test
    void getEmployees_shouldThrowException() throws InvalidEmployeeException {
        when(employeeRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        InvalidEmployeeException thrown = assertThrows(InvalidEmployeeException.class, () ->{
            getEmployeeUseCase.getEmployee(1L);
        }, "INVALID_EMPLOYEE_ID");

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());
    }

}