package semester3.project.sanomed.business.impl.PrescriptionService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.prescription.DeletePrescriptionUseCase;
import semester3.project.sanomed.persistence.PrescriptionRepository;

@Service
@AllArgsConstructor
public class DeletePrescriptionUseCaseImpl implements DeletePrescriptionUseCase {

    private final PrescriptionRepository prescriptionRepository;

    @Override
    public void deletePrescription(long prescriptionId) {
        this.prescriptionRepository.deleteById(prescriptionId);
    }
}
