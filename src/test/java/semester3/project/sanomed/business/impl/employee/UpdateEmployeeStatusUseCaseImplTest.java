package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.UpdateStatusEmployeeRequest;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateEmployeeStatusUseCaseImplTest {
    @Mock
    EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    UpdateEmployeeStatusUseCaseImpl updateEmployeeStatusUseCase;


    @Test
    public void testUpdateEmployeeStatus() {
//        UpdateStatusEmployeeRequest request = UpdateStatusEmployeeRequest.builder().id(1L).status(Status_Employee.ACTIVE).build();
//
//        EmployeeEntity employee = EmployeeEntity.builder().id(1L).status(Status_Employee.FIRED).personEmbeddable(
//                new PersonEmbeddable("Ivana", "Nedelkova", "nr@gmail.com", "123222", LocalDate.of(1980, 1, 1)))
//                .description("Doctor").build();
//
//        employeeRepositoryMock.save(employee);
//        when(employeeRepositoryMock.findById(1L)).thenReturn(Optional.of(employee));
//
//        updateEmployeeStatusUseCase.updateEmployeeStatus(request);
//
//        assertEquals(Status_Employee.ACTIVE, employeeRepositoryMock.findById(1L).get().getStatus());
        long employeeId = 1L;
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(employeeId)
                .personEmbeddable(new PersonEmbeddable("John", "Doe", "johndoe@email.com", "1234567890", LocalDate.now()))
                .status(Status_Employee.ACTIVE)
                .build();
        when(employeeRepositoryMock.findById(employeeId)).thenReturn(Optional.of(employeeEntity));
        UpdateStatusEmployeeRequest request = UpdateStatusEmployeeRequest.builder()
                .id(employeeId)
                .status(Status_Employee.FIRED)
                .build();

        updateEmployeeStatusUseCase.updateEmployeeStatus(request);

        assertEquals(Status_Employee.FIRED, employeeEntity.getStatus());
        verify(employeeRepositoryMock).findById(employeeId);
        verify(employeeRepositoryMock).save(employeeEntity);

    }

    @Test
    public void testUpdateEmployeeStatusUnhappyFlow() {
        UpdateStatusEmployeeRequest request = UpdateStatusEmployeeRequest.builder().id(1L).status(Status_Employee.ACTIVE).build();
        when(employeeRepositoryMock.findById(1L)).thenReturn(Optional.empty());
        try {
            updateEmployeeStatusUseCase.updateEmployeeStatus(request);
            fail("Expected InvalidEmployeeException to be thrown");
        } catch (InvalidEmployeeException e) {
        }
    }
}