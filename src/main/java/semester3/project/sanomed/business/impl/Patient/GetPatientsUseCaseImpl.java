package semester3.project.sanomed.business.impl.Patient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.converter.PatientConverter;
import semester3.project.sanomed.business.interfaces.patient.GetPatientsUseCase;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.response.GetPatientsResponse;
import semester3.project.sanomed.persistence.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetPatientsUseCaseImpl implements GetPatientsUseCase {

    private final PatientRepository patientRepository;

    @Override
    public GetPatientsResponse getPatients() {
        List<Patient> patients = patientRepository.findAll()
                .stream()
                .map(PatientConverter::convertWithoutDiagnosis)
                .collect(Collectors.toList());

        return GetPatientsResponse.builder()
                .patients(patients)
                .build();
    }
}
