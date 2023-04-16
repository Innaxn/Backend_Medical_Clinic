package semester3.project.sanomed.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import semester3.project.sanomed.domain.Status_Employee;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusEmployeeRequest {
    private Long id;
    private Status_Employee status;
}
