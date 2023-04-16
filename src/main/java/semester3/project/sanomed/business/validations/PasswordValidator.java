package semester3.project.sanomed.business.validations;

import semester3.project.sanomed.business.exceptions.InvalidPasswordException;

public interface PasswordValidator {
    void comparePasswords(String givenPassword, String password)throws InvalidPasswordException;;
}
