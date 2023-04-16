package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.converter.EmployeeConverter;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.impl.employee.GetEmployeeByLastNameImpl;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetEmployeeByLastNameImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    GetEmployeeByLastNameImpl getEmployeesLastNameUseCase;

    @Test
    public void getEmployeeByLastName() {
        String lastName = "Nedelkova";
        PersonEmbeddable personOne = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").build();
        PersonEmbeddable personTwo = PersonEmbeddable.builder().firstName("Shar").lastName("Nedelkova").build();
        Person personO = Person.builder().firstName("Ivana").lastName("Nedelkova").build();
        Person personT = Person.builder().firstName("Shar").lastName("Nedelkova").build();

        EmployeeEntity employee1 = EmployeeEntity.builder()
                .id(1L)
                .personEmbeddable(personOne)
                .status(Status_Employee.ACTIVE)
                .build();

        EmployeeEntity employee2 = EmployeeEntity.builder()
                .id(2L)
                .personEmbeddable(personTwo)
                .status(Status_Employee.ACTIVE)
                .build();


        Employee empOne = Employee.builder().id(1L)
                .person(personO)
                .status(Status_Employee.ACTIVE)
                .build();

        Employee empTwo= Employee.builder().id(2L)
                .person(personT)
                .status(Status_Employee.ACTIVE)
                .build();

        when(employeeRepository.findByDoctorByLastName(lastName, RoleEnum.DOCTOR, Status_Employee.ACTIVE))
                .thenReturn(List.of(employee1, employee2));


        GetEmployeesResponse actual = getEmployeesLastNameUseCase.getEmployeeByLastName(lastName);

        GetEmployeesResponse expectedResult = GetEmployeesResponse
                .builder()
                .employees(List.of(empOne,empTwo))
                .build();
        assertEquals(expectedResult, actual);
        verify(employeeRepository).findByDoctorByLastName(lastName, RoleEnum.DOCTOR, Status_Employee.ACTIVE);
    }

    @Test
    public void testGetEmployeeByLastName_unhappyFlow() {
        String lastName = "Nedelkova";

        when(employeeRepository.findByDoctorByLastName(lastName, RoleEnum.DOCTOR, Status_Employee.ACTIVE))
                .thenReturn(Collections.emptyList());

        InvalidEmployeeException thrown = assertThrows(
                InvalidEmployeeException.class,
                () -> getEmployeesLastNameUseCase.getEmployeeByLastName(lastName)
        );

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());
        verify(employeeRepository).findByDoctorByLastName(lastName, RoleEnum.DOCTOR, Status_Employee.ACTIVE );
    }
}