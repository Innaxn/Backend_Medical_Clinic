package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;
import semester3.project.sanomed.domain.Prescription;

import java.util.List;

@Builder
@Data
public class GetPrescriptionsResponse {
    private List<Prescription> prescriptions;
}
