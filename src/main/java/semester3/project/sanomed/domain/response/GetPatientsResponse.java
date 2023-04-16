package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;
import semester3.project.sanomed.domain.Patient;

import java.util.List;

@Data
@Builder
public class GetPatientsResponse {
    private List<Patient> patients;
}
