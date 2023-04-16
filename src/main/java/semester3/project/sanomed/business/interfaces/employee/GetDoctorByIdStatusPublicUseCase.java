package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.Employee;

public interface GetDoctorByIdStatusPublicUseCase {
    Employee getEmployee(long employeeId);
}
