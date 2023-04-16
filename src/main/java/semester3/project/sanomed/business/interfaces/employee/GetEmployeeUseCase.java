package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.Employee;

public interface GetEmployeeUseCase {
    Employee getEmployee(long employeeId);
}
