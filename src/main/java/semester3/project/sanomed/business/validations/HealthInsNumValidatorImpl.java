package semester3.project.sanomed.business.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidHealthInsNumException;
import semester3.project.sanomed.persistence.PatientRepository;

@Service
@AllArgsConstructor
public class HealthInsNumValidatorImpl implements HealthInsNumValidator {
    private PatientRepository patientRepository;

    @Override
    public void validateHealthInsNum(Integer healthInsNum) throws InvalidHealthInsNumException {
        if(patientRepository.existsByHealthInsuranceNumber(healthInsNum)){
            throw new InvalidHealthInsNumException();
        }
    }
}
