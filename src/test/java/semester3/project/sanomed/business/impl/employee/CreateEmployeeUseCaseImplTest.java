package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.business.impl.employee.CreateEmployeeUseCaseImpl;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.CreateAEmployeeRequest;
import semester3.project.sanomed.domain.request.CreateAppointmentRequest;
import semester3.project.sanomed.domain.response.CreateAppointmentResponse;
import semester3.project.sanomed.domain.response.CreateEmployeeResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.*;
import semester3.project.sanomed.persistence.UserRepository;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.given;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
class CreateEmployeeUseCaseImplTest {

    @Mock
    EmployeeRepository employeeRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;
    @Mock
    EmailValidator emailValidatorMock;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    CreateEmployeeUseCaseImpl createEmployeeUseCase;

    @Test
    void createEmployee() {
        PersonEmbeddable p = PersonEmbeddable.builder().firstName("Inna").lastName("Nedelkova").email("nrdelkova@gmail.com").build();
        EmployeeEntity emp = EmployeeEntity.builder().personEmbeddable(p).status(Status_Employee.ACTIVE).build();
        EmployeeEntity expected = EmployeeEntity.builder().id(1L).personEmbeddable(p).status(Status_Employee.ACTIVE).build();

        CreateAEmployeeRequest request = CreateAEmployeeRequest.builder().firstName("Inna").lastName("Nedelkova").email("nrdelkova@gmail.com").build();
        when(employeeRepositoryMock.save(emp)).thenReturn(expected);

        CreateEmployeeResponse response = createEmployeeUseCase.createEmployee(request);

        assertEquals(expected.getId(), response.getId());
        verify(emailValidatorMock, times(1)).validate(p.getEmail());
    }
}