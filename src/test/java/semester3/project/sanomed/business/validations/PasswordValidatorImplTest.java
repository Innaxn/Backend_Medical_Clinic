package semester3.project.sanomed.business.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import semester3.project.sanomed.business.exceptions.InvalidBsnException;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.business.exceptions.InvalidPasswordException;
import semester3.project.sanomed.persistence.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordValidatorImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordValidatorImpl passwordValidator;

    @Test
    void comparePasswords() {
        doThrow(InvalidPasswordException.class).when(passwordEncoder).matches("pwdd", "pwd");

        assertThrows(InvalidPasswordException.class, () -> passwordValidator.comparePasswords("pwdd", "pwd"));
    }
    @Test
    void pwdAlreadyInUse() {
        String raw = "12345";
        String pwd = "asd";
        when(passwordEncoder.matches(raw, pwd)).thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> {
            passwordValidator.comparePasswords(raw,pwd );
        });

    }

}