package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;
import semester3.project.sanomed.domain.Employee;

@Data
@Builder
public class GetEmployeeResponse {
    private Employee employee;
}
