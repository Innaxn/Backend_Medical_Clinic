package semester3.project.sanomed.domain;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Timeslot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
