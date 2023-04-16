package semester3.project.sanomed.business.interfaces.diagnose;

import semester3.project.sanomed.domain.Diagnose;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;

import java.util.Optional;

public interface GetDiagnoseUseCase {
    Optional<DiagnoseData> getDiagnose(long diagnoseId);
}
