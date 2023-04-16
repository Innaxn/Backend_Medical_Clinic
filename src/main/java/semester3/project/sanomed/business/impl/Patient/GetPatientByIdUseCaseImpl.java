package semester3.project.sanomed.business.impl.Patient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.converter.DiagnosisConverter;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.interfaces.patient.GetPatientByIdUseCase;
import semester3.project.sanomed.business.converter.PatientConverter;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.RoleEnum;
import semester3.project.sanomed.persistence.PatientRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class GetPatientByIdUseCaseImpl implements GetPatientByIdUseCase {

    private PatientRepository patientRepository;
    private AccessToken requestAccessToken;
    @Override
    public Patient getPatientById(long id) {

        if(requestAccessToken.hasRole(RoleEnum.PATIENT.name())){
            if(!Objects.equals(requestAccessToken.getPatientId(), id)){
                throw new UnauthorizedDataAccessException();
            }
        }

        Optional<PatientEntity> patientEntityOptional = patientRepository.findById(id);
        if(patientEntityOptional.isEmpty()){
            throw new InvalidPatientException();
        }

        PatientEntity patientEntity = patientEntityOptional.get();
        Patient patient = PatientConverter.convert(patientEntity);
        patient.setDiagnose(new ArrayList<>());
        for (DiagnoseEntity diag: patientEntity.getDiagnosis()) {
            patient.getDiagnose().add(DiagnosisConverter.convert(diag));
        }
        return patient;
    }
}
