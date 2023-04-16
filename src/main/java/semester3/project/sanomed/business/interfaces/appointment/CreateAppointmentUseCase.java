package semester3.project.sanomed.business.interfaces.appointment;


import semester3.project.sanomed.domain.request.CreateAppointmentRequest;
import semester3.project.sanomed.domain.response.CreateAppointmentResponse;

public interface CreateAppointmentUseCase {
    CreateAppointmentResponse createAppointment(CreateAppointmentRequest request);
}
