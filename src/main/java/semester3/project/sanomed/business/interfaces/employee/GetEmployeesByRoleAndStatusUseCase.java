package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

public interface GetEmployeesByRoleAndStatusUseCase {
    GetEmployeesResponse getEmployeesByRoleAndStatus(RoleEnum role, Status_Employee status);
}
