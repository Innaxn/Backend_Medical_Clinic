package semester3.project.sanomed.business.validations;

import semester3.project.sanomed.business.exceptions.InvalidBsnException;

public interface BsnValidator {
    void validateBsn(Integer bsn)throws InvalidBsnException;
}
