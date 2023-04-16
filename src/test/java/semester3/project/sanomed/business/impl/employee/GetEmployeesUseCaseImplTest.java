package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.converter.PatientConverter;
import semester3.project.sanomed.business.converter.PersonConverter;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.impl.employee.GetEmployeesUseCaseImpl;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class GetEmployeesUseCaseImplTest {

    @Mock
    private EmployeeRepository employeeRepositoryMock;
    @InjectMocks
    private GetEmployeesUseCaseImpl getEmployeesUseCase;

    @Test
    void getEmployees_shouldReturnAllEmployees() {

        PersonEmbeddable oneP = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com")
                .phoneNumber("0898939914").birthdate(LocalDate.of(1980,10,1)).build();

        PersonEmbeddable twoP = PersonEmbeddable.builder().firstName("Sharenda").lastName("Peeters").email("sharenda@gmail.com")
                .phoneNumber("0898939915").birthdate(LocalDate.of(1980,10,1)).build();

        EmployeeEntity empOneEntity = EmployeeEntity.builder()
                .personEmbeddable(oneP)
                .description("description")
                .status(Status_Employee.ACTIVE)
                .build();

        EmployeeEntity empTwoEntity= EmployeeEntity.builder()
                .personEmbeddable(twoP)
                .description("description")
                .status(Status_Employee.ACTIVE)
                .build();

        when(employeeRepositoryMock.findAll())
                .thenReturn(List.of(empOneEntity, empTwoEntity));

//        GetEmployeesUseCaseImpl getEmployeesUseCase = new GetEmployeesUseCaseImpl(employeeRepositoryMock);
        GetEmployeesResponse actualResult =getEmployeesUseCase.getEmployees();

        Employee empOne = Employee.builder()
                .person(PersonConverter.convert(oneP))
                .description("description")
                .status(Status_Employee.ACTIVE)
                .build();

        Employee empTwo= Employee.builder()
                .person(PersonConverter.convert(twoP))
                .description("description")
                .status(Status_Employee.ACTIVE)
                .build();

        GetEmployeesResponse expectedResult = GetEmployeesResponse
                .builder()
                .employees(List.of(empOne,empTwo))
                .build();

        assertEquals(expectedResult,actualResult);

        verify(employeeRepositoryMock).findAll();

    }

    @Test
    void getEmployees_shouldThrowException() throws InvalidEmployeeException {
        when(employeeRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());

        InvalidEmployeeException thrown = assertThrows(InvalidEmployeeException.class, () ->{
            getEmployeesUseCase.getEmployees();
        }, "INVALID_EMPLOYEE_ID");

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());

        verify(employeeRepositoryMock).findAll();
    }
}