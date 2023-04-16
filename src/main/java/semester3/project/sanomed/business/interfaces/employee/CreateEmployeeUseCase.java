package semester3.project.sanomed.business.interfaces.employee;

import semester3.project.sanomed.domain.request.CreateAEmployeeRequest;
import semester3.project.sanomed.domain.response.CreateEmployeeResponse;

public interface CreateEmployeeUseCase {

    CreateEmployeeResponse createEmployee(CreateAEmployeeRequest request);

}
