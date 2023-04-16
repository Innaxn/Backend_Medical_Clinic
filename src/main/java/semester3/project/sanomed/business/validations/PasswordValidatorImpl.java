package semester3.project.sanomed.business.validations;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidPasswordException;


@Service
@AllArgsConstructor
public class PasswordValidatorImpl implements  PasswordValidator{
    private final PasswordEncoder passwordEncoder;
    @Override
    public void comparePasswords(String givenPassword, String password) throws InvalidPasswordException {
        if(!passwordEncoder.matches(givenPassword, password)){
            throw new InvalidPasswordException();
        }
    }
}
