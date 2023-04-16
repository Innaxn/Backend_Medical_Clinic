package semester3.project.sanomed.business.validations;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.persistence.UserRepository;

@Service
@AllArgsConstructor
public class EmailValidatorImpl implements EmailValidator{

    private final UserRepository userRepository;

    @Override
    public void validate(String email) throws InvalidEmailException {

        if(userRepository.existsByEmail(email)){
            throw new InvalidEmailException();
        }

    }
}
