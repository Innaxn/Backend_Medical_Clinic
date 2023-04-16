package semester3.project.sanomed.business.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidBsnException;
import semester3.project.sanomed.persistence.PatientRepository;

@Service
@AllArgsConstructor
public class BsnValidatorImpl implements BsnValidator {

    private PatientRepository patientRepository;

    @Override
    public void validateBsn(Integer bsn) throws InvalidBsnException {
        if(patientRepository.existsByBsn(bsn)){
            throw new InvalidBsnException();
        }
    }
}
