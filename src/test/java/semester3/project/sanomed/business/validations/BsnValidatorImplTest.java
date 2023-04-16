package semester3.project.sanomed.business.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidBsnException;
import semester3.project.sanomed.business.exceptions.InvalidEmailException;
import semester3.project.sanomed.business.exceptions.InvalidHealthInsNumException;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.PatientRepository;
import semester3.project.sanomed.persistence.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BsnValidatorImplTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private BsnValidatorImpl bsnValidator;

//    @Test
//    void validateBsn() {
//        doThrow(InvalidEmailException.class).when(patientRepository).existsByBsn(12345);
//
//        assertThrows(InvalidEmailException.class, () -> bsnValidator.validateBsn(12345));
//    }
//
//    @Test
//    void testValidateBsn() {
//
//
//        try {
//            bsnValidator.validateBsn(123456788);
//        } catch (InvalidBsnException e) {
//            fail("Unexpected InvalidBsnException thrown");
//        }
//    }

    @Test
    void testBsnAlreadyInUse() {
        Integer bsn = 12345;
        when(patientRepository.existsByBsn(bsn)).thenReturn(true);

        assertThrows(InvalidBsnException.class, () -> {
            bsnValidator.validateBsn(bsn);
        });

    }
    @Test
    void testBsnNotInUse() {
        Integer bsn = 12345;
        when(patientRepository.existsByBsn(bsn)).thenReturn(false);

        bsnValidator.validateBsn(bsn);

    }
}