package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.converter.EmployeeConverter;
import semester3.project.sanomed.business.interfaces.employee.GetEmployeesUseCase;
import semester3.project.sanomed.business.interfaces.employee.GetFirstFiveUseCase;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetFirstFiveUseCaseImpl  implements GetFirstFiveUseCase {
    private final EmployeeRepository employeeRepository;

    @Override
    public GetEmployeesResponse getEmployeesFive() {
        List<Employee> employees = employeeRepository.showFive()
                .stream()
                .map(EmployeeConverter::convert)
                .collect(Collectors.toList());

        return GetEmployeesResponse.builder()
                .employees(employees)
                .build();

    }
}
