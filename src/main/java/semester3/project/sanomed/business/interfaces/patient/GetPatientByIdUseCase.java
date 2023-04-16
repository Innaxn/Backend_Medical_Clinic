package semester3.project.sanomed.business.interfaces.patient;

import semester3.project.sanomed.domain.Patient;

public interface GetPatientByIdUseCase {
    Patient getPatientById(long id);
}
