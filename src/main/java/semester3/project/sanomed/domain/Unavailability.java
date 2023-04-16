package semester3.project.sanomed.domain;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Unavailability {
    private Long id;
    private Employee emp;
    private LocalDate date;
}
