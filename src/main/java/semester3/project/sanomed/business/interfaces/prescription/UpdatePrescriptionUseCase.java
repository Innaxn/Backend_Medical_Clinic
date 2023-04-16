package semester3.project.sanomed.business.interfaces.prescription;

import semester3.project.sanomed.domain.request.UpdatePrescriptionRequest;

public interface UpdatePrescriptionUseCase {
    void updatePrescription(UpdatePrescriptionRequest request);
}
