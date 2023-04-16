package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GetDiagnosisResponse {
    private List<DiagnoseData> diagnosis;

    @Data
    @Builder
    public static class DiagnoseData {
        private Long id;
        private String name;
        private String details;
        private Long pId;
        private String FirstNamePatient;
        private String LastNamePatient;
        private Integer bsn;
        private Integer healthInsuranceNumber;
        private Long dId;
        private String FirstNameDoctor;
        private String LastNameDoctor;
    }
}