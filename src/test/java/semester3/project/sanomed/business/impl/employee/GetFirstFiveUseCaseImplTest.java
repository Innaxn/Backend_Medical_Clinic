package semester3.project.sanomed.business.impl.employee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetFirstFiveUseCaseImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    GetFirstFiveUseCaseImpl getFirstFiveUseCase;

    @Test
    void testGetEmployeesFive() {
        PersonEmbeddable p = PersonEmbeddable.builder().firstName("Inna").lastName("Nedelkova").email("nrdelkova@gmail.com").build();
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).personEmbeddable(p).status(Status_Employee.ACTIVE).build();
        EmployeeEntity emp2 = EmployeeEntity.builder().id(2L).personEmbeddable(p).status(Status_Employee.ACTIVE).build();
        EmployeeEntity emp3 = EmployeeEntity.builder().id(2L).personEmbeddable(p).status(Status_Employee.ACTIVE).build();
        EmployeeEntity emp4 = EmployeeEntity.builder().id(2L).personEmbeddable(p).status(Status_Employee.ACTIVE).build();
        EmployeeEntity emp5 = EmployeeEntity.builder().id(2L).personEmbeddable(p).status(Status_Employee.ACTIVE).build();

        List<EmployeeEntity> employeeEntities = List.of(emp,emp2,emp3,emp4,emp5);
        when(employeeRepository.showFive()).thenReturn(employeeEntities);

        GetEmployeesResponse response = getFirstFiveUseCase.getEmployeesFive();

        assertEquals(5, response.getEmployees().size());
    }
}