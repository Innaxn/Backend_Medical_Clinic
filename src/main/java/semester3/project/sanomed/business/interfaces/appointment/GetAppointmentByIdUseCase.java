package semester3.project.sanomed.business.interfaces.appointment;

import semester3.project.sanomed.domain.Appointment;

public interface GetAppointmentByIdUseCase {

    Appointment getAppointment(long id);
}
