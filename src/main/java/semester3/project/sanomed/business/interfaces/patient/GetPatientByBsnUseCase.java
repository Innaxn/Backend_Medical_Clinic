package semester3.project.sanomed.business.interfaces.patient;

import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.response.GetPatientsResponse;

public interface GetPatientByBsnUseCase {
    GetPatientsResponse getPatientsByBsn(Integer bsn);
}
