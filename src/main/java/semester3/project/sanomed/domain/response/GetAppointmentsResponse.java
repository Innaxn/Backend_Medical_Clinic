package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;
import semester3.project.sanomed.domain.Appointment;

import java.util.List;

@Data
@Builder
public class GetAppointmentsResponse {
    private List<Appointment> appointments;
}
