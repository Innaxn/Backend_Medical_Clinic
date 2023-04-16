package semester3.project.sanomed.business.impl.PrescriptionService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.prescription.GetPrescriptionsByDiagnoseIdUseCase;
import semester3.project.sanomed.business.converter.PrescriptionConverter;
import semester3.project.sanomed.business.exceptions.InvalidPrescriptionException;
import semester3.project.sanomed.domain.Prescription;
import semester3.project.sanomed.domain.response.GetPrescriptionsResponse;
import semester3.project.sanomed.persistence.PrescriptionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetPrescriptionsByDiagnoseIdUseCaseImpl implements GetPrescriptionsByDiagnoseIdUseCase {
    private PrescriptionRepository prescriptionRepository;

    @Override
    public GetPrescriptionsResponse getPrescriptionsByDiagnoseId(long id) {
        List<Prescription> prescriptions = prescriptionRepository.findByDiagnoseEntity_Id(id)
                .stream().map(PrescriptionConverter::convert).collect(Collectors.toList());
        if(prescriptions.isEmpty()){
            throw new InvalidPrescriptionException();
        }

        return GetPrescriptionsResponse.builder()
                .prescriptions(prescriptions)
                .build();
    }
}
