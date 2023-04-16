package semester3.project.sanomed.business.interfaces.appointment;

import semester3.project.sanomed.domain.response.GetAppointmentsResponse;

import java.time.LocalDateTime;

public interface GetAppointmentByPatientIdAndDateUseCase {
    GetAppointmentsResponse getAppointments(long pId, LocalDateTime date);
}
