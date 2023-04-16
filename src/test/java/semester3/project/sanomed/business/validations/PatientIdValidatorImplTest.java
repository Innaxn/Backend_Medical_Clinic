package semester3.project.sanomed.business.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidBsnException;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.persistence.PatientRepository;
import semester3.project.sanomed.persistence.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientIdValidatorImplTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientIdValidatorImpl patientIdValidator;

    @Test
    void patientAlreadyInUse() {
        long id = 12345;
        when(patientRepository.existsById(id)).thenReturn(false);

        assertThrows(InvalidPatientException.class, () -> {
            patientIdValidator.validateId(id);
        });

    }
    @Test
    void patientNotInUse() {
        long id = 12345;
        when(patientRepository.existsById(id)).thenReturn(true);

        patientIdValidator.validateId(id);

    }
}