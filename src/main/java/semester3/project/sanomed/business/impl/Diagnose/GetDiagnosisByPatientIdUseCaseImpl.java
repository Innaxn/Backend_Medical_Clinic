package semester3.project.sanomed.business.impl.Diagnose;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.interfaces.diagnose.GetDiagnosisByPatientIdUseCase;
import semester3.project.sanomed.business.converter.DiagnosisConverter;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetDiagnosisByPatientIdUseCaseImpl implements GetDiagnosisByPatientIdUseCase {

    private DiagnoseRepository diagnoseRepository;
    private AccessToken requestAccessToken;

    @Override
    public GetDiagnosisResponse getDiagnosisByPatientId(long patientId) {
        
        if(requestAccessToken.hasRole(RoleEnum.PATIENT.name())){
            if(!Objects.equals(requestAccessToken.getPatientId(), patientId)){
                throw new UnauthorizedDataAccessException();
            }
        }
        List<DiagnoseData> diagnosis = diagnoseRepository.findByPatient_Id(patientId)
                .stream().map(DiagnosisConverter::convertDataForDisplaying).collect(Collectors.toList());

        if(diagnosis.isEmpty()){
            throw new InvalidDiagnoseException();
        }

        return GetDiagnosisResponse.builder()
                .diagnosis(diagnosis)
                .build();
    }
}
