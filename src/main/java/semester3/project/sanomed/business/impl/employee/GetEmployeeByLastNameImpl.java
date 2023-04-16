package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.interfaces.employee.GetEmployeeByLastNameUseCase;
import semester3.project.sanomed.business.converter.EmployeeConverter;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetEmployeeByLastNameImpl implements GetEmployeeByLastNameUseCase {
    private EmployeeRepository employeeRepository;

    @Override
    public GetEmployeesResponse getEmployeeByLastName(String lastName) {

            List<Employee> employees =  employeeRepository.findByDoctorByLastName(lastName, RoleEnum.DOCTOR, Status_Employee.ACTIVE)
                    .stream()
                    .map(EmployeeConverter::convert)
                    .collect(Collectors.toList());

            if(employees.isEmpty()){
                throw new InvalidEmployeeException();
            }

            return GetEmployeesResponse.builder()
                    .employees(employees)
                    .build();
    }
}
