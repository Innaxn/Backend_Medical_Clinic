package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.impl.employee.GetEmployeesByRoleImpl;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.*;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEmployeesByRoleImplTest {
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    @InjectMocks
    private GetEmployeesByRoleImpl getEmployeesByRole;


    @Test
    void getEmployees() {
        PersonEmbeddable oneP = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("12345").birthdate(LocalDate.of(1980,10,1)).build();
        PersonEmbeddable twoP = PersonEmbeddable.builder().firstName("Sharenda").lastName("Peeters").email("sharenda@gmail.com").phoneNumber("12345").birthdate(LocalDate.of(1980,10,1)).build();

        Person one = Person.builder().firstName("Ivana").lastName("Nedelkova").email("nrdelkova@gmail.com").phoneNumber("12345").birthdate(LocalDate.of(1980,10,1)).build();
        Person two = Person.builder().firstName("Sharenda").lastName("Peeters").email("sharenda@gmail.com").phoneNumber("12345").birthdate(LocalDate.of(1980,10,1)).build();


        EmployeeEntity empOneEntity = EmployeeEntity.builder().id(1L)
                .personEmbeddable(oneP)
                .description("description")
                .build();

        EmployeeEntity empTwoEntity= EmployeeEntity.builder().id(2L)
                .personEmbeddable(twoP)
                .description("description")
                .build();

        UserEntity newUser = UserEntity.builder()
                .email("nrdelkova@gmail.com")
                .password("encodedPassword")
                .emp(empOneEntity)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.DOCTOR)
                        .build()));

        UserEntity newUser2 = UserEntity.builder()
                .email("sharenda@gmail.com")
                .password("encodedPassword")
                .emp(empOneEntity)
                .build();

        newUser2.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser2)
                        .role(RoleEnum.DOCTOR)
                        .build()));

        when(employeeRepositoryMock.findEmployeeByRole(RoleEnum.DOCTOR))
                .thenReturn(List.of(empOneEntity, empTwoEntity));

        GetEmployeesResponse actualResult =getEmployeesByRole.getEmployees(RoleEnum.DOCTOR);

        Employee empOne = Employee.builder().id(1L)
                .person(one)
                .description("description")
                .build();

        Employee empTwo= Employee.builder().id(2L)
                .person(two)
                .description("description")
                .build();

        GetEmployeesResponse expectedResult = GetEmployeesResponse
                .builder()
                .employees(List.of(empOne,empTwo))
                .build();

        assertEquals(expectedResult,actualResult);

        verify(employeeRepositoryMock).findEmployeeByRole(RoleEnum.DOCTOR);
    }

    @Test
    void getEmployees_shouldThrowException() throws InvalidEmployeeException {
        when(employeeRepositoryMock.findEmployeeByRole(RoleEnum.DOCTOR))
                .thenReturn(Collections.emptyList());

        InvalidEmployeeException thrown = assertThrows(InvalidEmployeeException.class, () ->{
            getEmployeesByRole.getEmployees(RoleEnum.DOCTOR);
        }, "INVALID_EMPLOYEE_ID");

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());

        verify(employeeRepositoryMock).findEmployeeByRole(RoleEnum.DOCTOR);
    }
}