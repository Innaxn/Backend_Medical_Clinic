package semester3.project.sanomed.business.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.persistence.PatientRepository;

@Service
@AllArgsConstructor
public class PatientIdValidatorImpl implements PatientIdValidator {

    private final PatientRepository patientRepository;

    @Override
    public void validateId(Long patientId) throws InvalidPatientException {
        if(patientId == null || !patientRepository.existsById(patientId)){
            throw new InvalidPatientException();
        }
    }
}
