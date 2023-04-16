package semester3.project.sanomed.business.impl.PrescriptionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import semester3.project.sanomed.persistence.PrescriptionRepository;

@ExtendWith(MockitoExtension.class)
class DeletePrescriptionUseCaseImplTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private DeletePrescriptionUseCaseImpl deletePrescriptionUseCase;

    @Test
    void deletePrescription() {
        long id= 1;
        deletePrescriptionUseCase.deletePrescription(1);

        verify(prescriptionRepository, times(1)).deleteById(id);
    }
}