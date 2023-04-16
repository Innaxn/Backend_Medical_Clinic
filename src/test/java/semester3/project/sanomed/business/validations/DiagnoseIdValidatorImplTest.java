package semester3.project.sanomed.business.validations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.persistence.DiagnoseRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiagnoseIdValidatorImplTest {
    @Mock
    private DiagnoseRepository diagnoseRepository;

    @InjectMocks
    private DiagnoseIdValidatorImpl diagnoseIdValidator;

    @Test
    void diagnoseAlreadyInUse() {
        long id=45;
        when(diagnoseRepository.existsById(id)).thenReturn(false);

        assertThrows(InvalidDiagnoseException.class, () -> {
            diagnoseIdValidator.validateId(id);
        });

    }
    @Test
    void diagnoseNotInUse() {
        long id=45;
        when(diagnoseRepository.existsById(id)).thenReturn(true);

        diagnoseIdValidator.validateId(id);

    }
}