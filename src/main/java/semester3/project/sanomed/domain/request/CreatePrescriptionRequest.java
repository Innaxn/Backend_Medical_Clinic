package semester3.project.sanomed.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePrescriptionRequest {
    @NotBlank
    private LocalDate start;
    @NotBlank
    private LocalDate end;
    @NotBlank
    private String medication;
    @NotBlank
    private Long doctorId;
    @NotBlank
    private Long diagnoseId;
}
