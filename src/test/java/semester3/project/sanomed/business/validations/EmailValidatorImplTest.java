package semester3.project.sanomed.business.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidBsnException;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.domain.request.CreatePrescriptionRequest;
import semester3.project.sanomed.persistence.UserRepository;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailValidatorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailValidatorImpl emailValidator;

    @Test
    void emailAlreadyInUse() {
        String email = "email@gmail.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(InvalidEmailException.class, () -> {
            emailValidator.validate(email);
        });

    }
    @Test
    void emailNotInUse() {
        String email = "email@gmail.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        emailValidator.validate(email);

    }
}