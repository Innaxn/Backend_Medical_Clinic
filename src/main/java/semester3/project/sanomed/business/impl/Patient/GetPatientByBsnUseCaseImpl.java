package semester3.project.sanomed.business.impl.Patient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.patient.GetPatientByBsnUseCase;
import semester3.project.sanomed.business.converter.PatientConverter;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.response.GetPatientsResponse;
import semester3.project.sanomed.persistence.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetPatientByBsnUseCaseImpl implements GetPatientByBsnUseCase {
    private PatientRepository patientRepository;
    @Override
    public GetPatientsResponse getPatientsByBsn(Integer bsn) {
        String newBsn = String.valueOf(bsn);
        List<Patient> patients = patientRepository.findByBsnContaining(newBsn)
                .stream()
                .map(PatientConverter::convertWithoutDiagnosis)
                .collect(Collectors.toList());

        if(patients.isEmpty()){
            throw new InvalidPatientException();
        }

        return GetPatientsResponse.builder()
                .patients(patients)
                .build();
    }
}
