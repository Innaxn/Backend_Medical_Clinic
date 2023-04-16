package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.request.UpdateStatusEmployeeRequest;

public interface UpdateEmployeeStatusUseCase {
    void updateEmployeeStatus(UpdateStatusEmployeeRequest request);
}
