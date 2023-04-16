package semester3.project.sanomed.business.validations;

import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;

public interface EmployeeIdValidator {

    void validateId(Long employeeId)throws InvalidEmployeeException;

}
