package semester3.project.sanomed.business.impl.Diagnose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.persistence.DiagnoseRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDiagnosisUseCaseImplTest {

    @Mock
    private DiagnoseRepository diagnoseRepositoryMock;
    @InjectMocks
    private DeleteDiagnosisUseCaseImpl deleteDiagnosisUseCase;

    @Test
    void deleteDiagnose() {
        long id= 1;
        deleteDiagnosisUseCase.deleteDiagnose(1);

        verify(diagnoseRepositoryMock, times(1)).deleteById(id);
    }
}