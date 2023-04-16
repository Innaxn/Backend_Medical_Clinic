package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;

public interface UpdateEmployeeUseCase {
    void updateEmployee(UpdateBasicEmployeeRequest request);
}
