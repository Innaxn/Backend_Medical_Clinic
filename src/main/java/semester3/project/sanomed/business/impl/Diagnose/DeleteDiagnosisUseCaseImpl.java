package semester3.project.sanomed.business.impl.Diagnose;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.diagnose.DeleteDiagnosisUseCase;
import semester3.project.sanomed.persistence.DiagnoseRepository;

@Service
@AllArgsConstructor
public class DeleteDiagnosisUseCaseImpl implements DeleteDiagnosisUseCase {
    private final DiagnoseRepository diagnosisRepository;

    @Override
    public void deleteDiagnose(long diagnoseId) {

        this.diagnosisRepository.deleteById(diagnoseId);
    }
}
