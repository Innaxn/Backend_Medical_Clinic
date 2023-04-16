package semester3.project.sanomed.business.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.persistence.EmployeeRepository;


@Service
@AllArgsConstructor
public class EmployeeIdValidatorImpl implements EmployeeIdValidator {

    private final EmployeeRepository employeeRepository;

    @Override
    public void validateId(Long employeeId) throws InvalidEmployeeException {
        if(employeeId == null || !employeeRepository.existsById(employeeId)){
            throw new InvalidEmployeeException();
        }
    }
}
