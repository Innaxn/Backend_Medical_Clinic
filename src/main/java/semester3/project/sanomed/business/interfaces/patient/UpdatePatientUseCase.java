package semester3.project.sanomed.business.interfaces.patient;


import semester3.project.sanomed.domain.request.UpdateBasicPatientRequest;

public interface UpdatePatientUseCase {
    void updatePatient(UpdateBasicPatientRequest request);
}
