package semester3.project.sanomed.business.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.persistence.DiagnoseRepository;


@Service
@AllArgsConstructor
public class DiagnoseIdValidatorImpl implements DiagnoseIdValidator {
    private final DiagnoseRepository diagnoseRepository;

    @Override
    public void validateId(Long diagnoseId) throws InvalidDiagnoseException {
        if(diagnoseId == null || !diagnoseRepository.existsById(diagnoseId)){
            throw new InvalidDiagnoseException();
        }
    }
}
