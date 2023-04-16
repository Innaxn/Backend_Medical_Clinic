package semester3.project.sanomed.business.impl.Diagnose;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.diagnose.GetDiagnoseUseCase;
import semester3.project.sanomed.business.converter.DiagnosisConverter;
import semester3.project.sanomed.domain.Diagnose;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;

import java.util.Optional;
@Service
@AllArgsConstructor
public class GetDiagnoseUseCaseImpl implements GetDiagnoseUseCase {
    private DiagnoseRepository diagnoseRepository;

    @Override
    public Optional<DiagnoseData> getDiagnose(long diagnoseId) {
        return diagnoseRepository.findById(diagnoseId).map(DiagnosisConverter::convertDataForDisplaying);
    }
}
