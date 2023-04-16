package semester3.project.sanomed.business.interfaces.prescription;

import semester3.project.sanomed.domain.response.GetPrescriptionsResponse;

public interface GetPrescriptionsByDiagnoseIdUseCase {
    GetPrescriptionsResponse getPrescriptionsByDiagnoseId(long id);
}


