package semester3.project.sanomed.business.impl.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.persistence.AppointmentRepository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private DeleteAppointmentUseCaseImpl deleteAppointmentUseCase;

    @Test
    void deleteAppointment() {
        long id= 1;
        deleteAppointmentUseCase.deleteAppointment(1);

        verify(appointmentRepository, times(1)).deleteById(id);
    }
}