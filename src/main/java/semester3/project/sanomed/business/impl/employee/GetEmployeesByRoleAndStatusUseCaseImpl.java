package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.converter.EmployeeConverter;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.interfaces.employee.GetEmployeesByRoleAndStatusUseCase;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetEmployeesByRoleAndStatusUseCaseImpl  implements GetEmployeesByRoleAndStatusUseCase {
    private EmployeeRepository employeeRepository;
    @Override
    public GetEmployeesResponse getEmployeesByRoleAndStatus(RoleEnum role, Status_Employee status) {
        List<Employee> employees = employeeRepository.findEmployeeByRoleAndStatus(role, status).stream()
                .map(EmployeeConverter::convert).collect(Collectors.toList());
        if(employees.isEmpty()){
            throw new InvalidEmployeeException();
        }

        return GetEmployeesResponse.builder()
                .employees(employees)
                .build();
    }
}
