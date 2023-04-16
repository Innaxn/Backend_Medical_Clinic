package semester3.project.sanomed.business.interfaces.patient;

import semester3.project.sanomed.domain.request.CreatePatientRequest;
import semester3.project.sanomed.domain.response.CreatePatientResponse;

public interface CreatePatientUseCase {
    CreatePatientResponse createPatient(CreatePatientRequest request);
}
