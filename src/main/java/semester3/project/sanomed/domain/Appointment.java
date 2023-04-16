package semester3.project.sanomed.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Appointment {
     private Long id;
     private Employee doctor;
     private Patient patient;
     private LocalDateTime start;
     private String purpose;
}
