package semester3.project.sanomed.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Diagnose {

    private Long id;
    private String name;
    private String details;
    private Patient patient;
    private Employee doctor;
}
