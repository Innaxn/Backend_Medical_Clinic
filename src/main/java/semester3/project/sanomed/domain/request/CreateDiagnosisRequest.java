package semester3.project.sanomed.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDiagnosisRequest {
    private String name;
    private String details;
    private Long patientId;
    private Long doctorId;
}
