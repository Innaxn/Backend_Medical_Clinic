package semester3.project.sanomed.business.interfaces.appointment;

import semester3.project.sanomed.domain.response.GetAppointmentsResponse;

import java.time.LocalDateTime;

public interface GetAppointmentByDocIdAndFromDatesUseCase {
     GetAppointmentsResponse getAppointments(long dId, LocalDateTime date);
}
