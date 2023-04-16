package semester3.project.sanomed.business.validations;

import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;

public interface DiagnoseIdValidator {

    void validateId(Long diagnoseId)throws InvalidDiagnoseException;

}
