package semester3.project.sanomed.business.interfaces.diagnose;

import semester3.project.sanomed.domain.response.CreateDiagnoseResponse;
import semester3.project.sanomed.domain.request.CreateDiagnosisRequest;

public interface CreateDiagnoseUseCase {
    CreateDiagnoseResponse createDiagnose(CreateDiagnosisRequest request);
}
