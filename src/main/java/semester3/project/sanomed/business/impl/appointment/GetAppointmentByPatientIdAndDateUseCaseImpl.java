package semester3.project.sanomed.business.impl.appointment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import semester3.project.sanomed.business.interfaces.appointment.GetAppointmentByPatientIdAndDateUseCase;
import semester3.project.sanomed.business.converter.AppointmentConverter;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.domain.AccessToken;
import semester3.project.sanomed.domain.Appointment;
import semester3.project.sanomed.domain.response.GetAppointmentsResponse;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAppointmentByPatientIdAndDateUseCaseImpl implements GetAppointmentByPatientIdAndDateUseCase {
    private AppointmentRepository appointmentRepository;
    private AccessToken requestAccessToken;

    @Override
    public GetAppointmentsResponse getAppointments(long pId, LocalDateTime date) {
        if(requestAccessToken.hasRole(RoleEnum.PATIENT.name())){
            if(!Objects.equals(requestAccessToken.getPatientId(), pId)){
                throw new UnauthorizedDataAccessException();
            }
        }
        List<Appointment> appointments = appointmentRepository.findAppointmentEntitiesByPatient_IdAndDateAfter(pId, date).stream()
                .map(AppointmentConverter::convert).collect(Collectors.toList());
        if (appointments.isEmpty()) {
            throw new InvalidAppointmentException();
        }

        return GetAppointmentsResponse.builder()
                .appointments(appointments)
                .build();
    }
}