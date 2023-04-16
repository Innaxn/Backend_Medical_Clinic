package semester3.project.sanomed.business.impl.appointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.appointment.GetAppointmentByIdUseCase;
import semester3.project.sanomed.business.converter.AppointmentConverter;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.domain.Appointment;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetAppointmentByIdUseCaseImpl implements GetAppointmentByIdUseCase {

    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment getAppointment(long id) {
        Optional<AppointmentEntity> appointmentEntityOptional = appointmentRepository.findById(id);
        if(appointmentEntityOptional.isEmpty()){
            throw new InvalidAppointmentException();
        }

        AppointmentEntity appointmentEntity = appointmentEntityOptional.get();
        Appointment appointment = AppointmentConverter.convert(appointmentEntity);

        return appointment;
    }
}
