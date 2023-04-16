package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

public interface GetEmployeesByRole {
    GetEmployeesResponse getEmployees(RoleEnum role);
}
