package semester3.project.sanomed.business.interfaces.appointment;

import semester3.project.sanomed.domain.request.UpdateAppointmentRequest;


public interface UpdateAppointmentUseCase {
    void updateAppointment(UpdateAppointmentRequest request);
}
