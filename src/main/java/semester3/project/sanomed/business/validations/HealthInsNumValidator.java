package semester3.project.sanomed.business.validations;

import semester3.project.sanomed.business.exceptions.InvalidHealthInsNumException;

public interface HealthInsNumValidator {
    void validateHealthInsNum(Integer healthInsNum)throws InvalidHealthInsNumException;
}
