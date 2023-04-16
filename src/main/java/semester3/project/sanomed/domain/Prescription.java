package semester3.project.sanomed.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Prescription {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private String medication;
    private Employee doctor;
    private Diagnose diagnose;
}
