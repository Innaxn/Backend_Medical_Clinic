package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidUserException;
import semester3.project.sanomed.business.validations.EmailValidator;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.Entity.UserEntity;
import semester3.project.sanomed.persistence.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEmployeeUseCaseImplTest {
    @Mock
    private EmployeeRepository employeeRepositoryMock;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private EmailValidator emailValidator;
    @InjectMocks
    UpdateEmployeeUseCaseImpl updateEmployeeUseCase;

    @Test
    void updateEmployee_shouldUpdateEmployee() {
        long employeeId = 1L;
        UpdateBasicEmployeeRequest request = UpdateBasicEmployeeRequest.builder()
                .id(employeeId)
                .firstName("Ivana")
                .lastName("Nedelkova")
                .email("email@email.com")
                .phone("0987654321")
                .description("Test Description")
                .build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(employeeId)
                .personEmbeddable(new PersonEmbeddable("Ivana", "Nedelkova", "email@email.com", "1234567890", LocalDate.now()))
                .description("Test Description")
                .status(Status_Employee.ACTIVE)
                .build();
        UserEntity userEntity = UserEntity.builder()
                .email("email@email.com")
                .password("password")
                .emp(employeeEntity)
                .build();
        when(employeeRepositoryMock.findById(employeeId)).thenReturn(Optional.of(employeeEntity));
        when(userRepository.findByEmp_Id(employeeId)).thenReturn(Optional.of(userEntity));

        updateEmployeeUseCase.updateEmployee(request);

        assertEquals(request.getFirstName(), employeeEntity.getPersonEmbeddable().getFirstName());
        assertEquals(request.getLastName(), employeeEntity.getPersonEmbeddable().getLastName());
        assertEquals(request.getEmail(), employeeEntity.getPersonEmbeddable().getEmail());
        assertEquals(request.getPhone(), employeeEntity.getPersonEmbeddable().getPhoneNumber());
        assertEquals(request.getDescription(), employeeEntity.getDescription());
        assertEquals(request.getEmail(), userEntity.getEmail());
        verify(employeeRepositoryMock).findById(employeeId);
        verify(userRepository).findByEmp_Id(employeeId);
        verify(employeeRepositoryMock).save(employeeEntity);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testValidUpdate() {
        UpdateBasicEmployeeRequest request = UpdateBasicEmployeeRequest.builder().id(1L).firstName("Inna").lastName("Nedelkova")
                .email("email").phone("08754521566").description("description").build();
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(1L);
        employee.setPersonEmbeddable(new PersonEmbeddable("first", "last", "email", "phone", null));
        employee.setDescription("description");
        employee.setStatus(Status_Employee.ACTIVE);
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("email");
        when(employeeRepositoryMock.findById(request.getId())).thenReturn(Optional.of(employee));
        when(userRepository.findByEmp_Id(request.getId())).thenReturn(Optional.of(user));

        updateEmployeeUseCase.updateEmployee(request);
    }

    @Test
    void testInvalidEmployee() {
        UpdateBasicEmployeeRequest request = UpdateBasicEmployeeRequest.builder().id(1L).firstName("Inna").lastName("Nedelkova")
                .email("email").phone("08754521566").description("description").build();
        when(employeeRepositoryMock.findById(request.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidEmployeeException.class, () -> {
            updateEmployeeUseCase.updateEmployee(request);
        });
    }

    @Test
    void testInvalidUser() {
        UpdateBasicEmployeeRequest request = UpdateBasicEmployeeRequest.builder().id(1L).firstName("Inna").lastName("Nedelkova")
                .email("email").phone("08754521566").description("description").build();
        EmployeeEntity employee = new EmployeeEntity();
        employee.setId(1L);
        employee.setPersonEmbeddable(new PersonEmbeddable("first", "last", "email", "phone", null));
        employee.setDescription("description");
        employee.setStatus(Status_Employee.ACTIVE);
        when(employeeRepositoryMock.findById(request.getId())).thenReturn(Optional.of(employee));
        when(userRepository.findByEmp_Id(request.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidUserException.class, () -> {
            updateEmployeeUseCase.updateEmployee(request);
        });
    }
}