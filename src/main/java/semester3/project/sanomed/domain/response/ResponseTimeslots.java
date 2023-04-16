package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;
import semester3.project.sanomed.business.impl.Timeslots;

import java.util.List;
@Builder
@Data
public class ResponseTimeslots {
    List<Timeslots> ts;
}
