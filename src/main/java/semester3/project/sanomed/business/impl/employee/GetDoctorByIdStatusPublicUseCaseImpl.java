package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.converter.EmployeeConverter;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.interfaces.employee.GetDoctorByIdStatusPublicUseCase;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetDoctorByIdStatusPublicUseCaseImpl implements GetDoctorByIdStatusPublicUseCase {

    private EmployeeRepository employeeRepository;

    @Override
    public Employee getEmployee(long employeeId) {

        Optional<EmployeeEntity> emp = employeeRepository.findEmployeeByRoleAndStatusAndId(employeeId,  RoleEnum.DOCTOR,Status_Employee.ACTIVE);
        if (emp.isEmpty()) {
            throw new InvalidEmployeeException();
        }

        EmployeeEntity employeeEntity = emp.get();
        Employee newEmployee = EmployeeConverter.convert(employeeEntity);

        return newEmployee;
    }
}
