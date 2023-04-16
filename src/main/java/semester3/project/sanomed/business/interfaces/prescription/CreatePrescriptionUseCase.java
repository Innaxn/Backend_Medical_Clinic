package semester3.project.sanomed.business.interfaces.prescription;

import semester3.project.sanomed.domain.request.CreatePrescriptionRequest;
import semester3.project.sanomed.domain.response.CreatePrescriptionResponse;

public interface CreatePrescriptionUseCase {

    CreatePrescriptionResponse createPrescription(CreatePrescriptionRequest request);
}
