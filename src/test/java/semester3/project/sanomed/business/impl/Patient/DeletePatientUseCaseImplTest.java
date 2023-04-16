package semester3.project.sanomed.business.impl.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.persistence.PatientRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletePatientUseCaseImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private DeletePatientUseCaseImpl deletePatientUseCase;

    @Test
    void deletePatient() {
        long id= 1;
        deletePatientUseCase.deletePatient(1);

        verify(patientRepository, times(1)).deleteById(id);
    }
}