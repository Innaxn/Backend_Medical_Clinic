package semester3.project.sanomed.business.interfaces.diagnose;

import semester3.project.sanomed.domain.response.GetDiagnosisResponse;

public interface GetDiagnosisByPatientIdUseCase {
    GetDiagnosisResponse getDiagnosisByPatientId(long patientId);

}
