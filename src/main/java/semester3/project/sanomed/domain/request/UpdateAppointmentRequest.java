package semester3.project.sanomed.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateAppointmentRequest {
    private Long id;
    private long docId;
    @NotNull
    private long patientid;
    private LocalDateTime startDate;
    private String purpose;
}
