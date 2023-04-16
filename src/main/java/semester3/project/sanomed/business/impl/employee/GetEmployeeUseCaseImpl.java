package semester3.project.sanomed.business.impl.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.interfaces.employee.GetEmployeeUseCase;
import semester3.project.sanomed.business.converter.EmployeeConverter;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class  GetEmployeeUseCaseImpl implements GetEmployeeUseCase {

    private EmployeeRepository employeeRepository;
    private AccessToken requestAccessToken;

    @Override
    public Employee getEmployee(long employeeId) {

        String r = requestAccessToken.getRoles().toString();
        if(r.equals(RoleEnum.DOCTOR.toString()) || r.equals(RoleEnum.SECRETARY.toString()) || r.equals(RoleEnum.ADMINISTRATOR.toString()) ){
            if(!Objects.equals(requestAccessToken.getEmployeeId(), employeeId)){
                throw new UnauthorizedDataAccessException();
            }
        }
        Optional<EmployeeEntity> emp = employeeRepository.findById(employeeId);
        if(emp.isEmpty()){
           throw new InvalidEmployeeException();
        }

        EmployeeEntity employeeEntity = emp.get();
        Employee newEmployee = EmployeeConverter.convert(employeeEntity);

        return newEmployee;
    }

}
