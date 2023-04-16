package semester3.project.sanomed.business.validations;

import semester3.project.sanomed.business.exceptions.InvalidEmailException;

public interface EmailValidator {
    void validate(String email)throws InvalidEmailException;
}
