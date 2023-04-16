package semester3.project.sanomed.business.impl.appointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.appointment.DeleteAppointmentUseCase;
import semester3.project.sanomed.persistence.AppointmentRepository;

@Service
@AllArgsConstructor
public class DeleteAppointmentUseCaseImpl implements DeleteAppointmentUseCase {

    private AppointmentRepository appointmentRepository;

    @Override
    public void deleteAppointment(long id) {
        this.appointmentRepository.deleteById(id);
    }
}
