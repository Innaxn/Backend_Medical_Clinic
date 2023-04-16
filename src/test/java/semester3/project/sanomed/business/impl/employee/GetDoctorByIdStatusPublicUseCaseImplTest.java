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
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.*;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDoctorByIdStatusPublicUseCaseImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    GetDoctorByIdStatusPublicUseCaseImpl getDoctorByIdStatusPublicUseCase;


    @Test
    public void getEmployee_shouldBeOk() {
        PersonEmbeddable personEmbeddable =PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").email("email@gmail.com").phoneNumber("09292922").build();
        Person person = Person.builder().firstName("Ivana").lastName("Nedelkova").email("email@gmail.com").phoneNumber("09292922").build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).status(Status_Employee.ACTIVE).personEmbeddable(personEmbeddable).build();

        UserEntity newUser = UserEntity.builder()
                .email("nrdelkova@gmail.com")
                .password("encodedPassword")
                .emp(employeeEntity)
                .build();

        newUser.setUserRoles(Set.of(
                UserRoleEntity.builder()
                        .user(newUser)
                        .role(RoleEnum.DOCTOR)
                        .build()));

        Employee expectedEmployee = Employee.builder().id(1L).status(Status_Employee.ACTIVE).person(person).build();

        when(employeeRepository.findEmployeeByRoleAndStatusAndId(1L, RoleEnum.DOCTOR, Status_Employee.ACTIVE)).thenReturn(Optional.of(employeeEntity));

        Employee actualEmployee = getDoctorByIdStatusPublicUseCase.getEmployee(1L);
        assertEquals(expectedEmployee, actualEmployee);
    }

    @Test
    public void test_unhappyFlow() {

        when(employeeRepository.findEmployeeByRoleAndStatusAndId(1L, RoleEnum.DOCTOR, Status_Employee.ACTIVE)).thenReturn(Optional.empty());

        InvalidEmployeeException thrown = assertThrows(
                InvalidEmployeeException.class,
                () -> getDoctorByIdStatusPublicUseCase.getEmployee(1L)
        );

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());
    }


}