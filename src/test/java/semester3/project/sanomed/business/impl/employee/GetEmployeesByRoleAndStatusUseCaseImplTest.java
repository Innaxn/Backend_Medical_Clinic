package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEmployeesByRoleAndStatusUseCaseImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    GetEmployeesByRoleAndStatusUseCaseImpl getEmployeesByRoleAndStatusUseCase;
    @Test
    void getEmployeesByRoleAndStatus() {
    }
    @Test
    public void testGetEmployeesByRoleAndStatus_validInput() {
        PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Ivana").firstName("Nedelkova").email("email@email.com").build();
        Person person =  Person.builder().firstName("Ivana").firstName("Nedelkova").email("email@email.com").build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).personEmbeddable(personEmbeddable).status(Status_Employee.ACTIVE).build();
        Employee employee = Employee.builder().id(1L).person(person).status(Status_Employee.ACTIVE).build();

        RoleEnum role = RoleEnum.DOCTOR;
        Status_Employee status = Status_Employee.ACTIVE;
        when(employeeRepository.findEmployeeByRoleAndStatus(role, status))
                .thenReturn(List.of(employeeEntity));

        GetEmployeesResponse actual = getEmployeesByRoleAndStatusUseCase.getEmployeesByRoleAndStatus(role, status);
        GetEmployeesResponse response = GetEmployeesResponse.builder().employees(List.of(employee)).build();
        assertEquals(actual, response);
        verify(employeeRepository).findEmployeeByRoleAndStatus(RoleEnum.DOCTOR, Status_Employee.ACTIVE );
    }


    @Test
    public void testgetEmployeesByRoleAndStatus_unhappyFlow() {

        when(employeeRepository.findEmployeeByRoleAndStatus(RoleEnum.DOCTOR, Status_Employee.ACTIVE))
                .thenReturn(Collections.emptyList());

        InvalidEmployeeException thrown = assertThrows(
                InvalidEmployeeException.class,
                () -> getEmployeesByRoleAndStatusUseCase.getEmployeesByRoleAndStatus(RoleEnum.DOCTOR, Status_Employee.ACTIVE)
        );

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());
        verify(employeeRepository).findEmployeeByRoleAndStatus(RoleEnum.DOCTOR, Status_Employee.ACTIVE );
    }
}