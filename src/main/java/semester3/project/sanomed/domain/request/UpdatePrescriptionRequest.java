package semester3.project.sanomed.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePrescriptionRequest {
    private Long id;
    @NotNull
    private LocalDate start;
    @NotNull
    private LocalDate end;
    @NotBlank
    private String medication;
    private Long doctorId;
}
