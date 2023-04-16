package semester3.project.sanomed.business.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidBsnException;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.business.exceptions.InvalidHealthInsNumException;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.PatientRepository;
import semester3.project.sanomed.persistence.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthInsNumValidatorImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private HealthInsNumValidatorImpl healthInsNumValidator;

    @Test
    void validateHealthInsNum() {
        doThrow(InvalidEmailException.class).when(patientRepository).existsByHealthInsuranceNumber(12345);

        assertThrows(InvalidEmailException.class, () -> healthInsNumValidator.validateHealthInsNum(12345));
    }

    @Test
    void testBsnAlreadyInUse() {
        Integer num = 12345;
        when(patientRepository.existsByHealthInsuranceNumber(num)).thenReturn(true);

        assertThrows(InvalidHealthInsNumException.class, () -> {
            healthInsNumValidator.validateHealthInsNum(num);
        });

    }
    @Test
    void testBsnNotInUse() {
        Integer num = 12345;
        when(patientRepository.existsByHealthInsuranceNumber(num)).thenReturn(false);

        healthInsNumValidator.validateHealthInsNum(num);

    }


}