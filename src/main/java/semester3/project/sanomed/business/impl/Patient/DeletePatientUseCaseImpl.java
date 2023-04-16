package semester3.project.sanomed.business.impl.Patient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.patient.DeletePatientUseCase;
import semester3.project.sanomed.persistence.PatientRepository;

@Service
@AllArgsConstructor
public class DeletePatientUseCaseImpl implements DeletePatientUseCase {
    private final PatientRepository patientRepository;

    @Override
    public void deletePatient(long patientId) {
        this.patientRepository.deleteById(patientId);
    }
}
