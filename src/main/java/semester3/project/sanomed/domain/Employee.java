package semester3.project.sanomed.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Employee {
    private Long id;
    private String description;
    private Person person;
    private Status_Employee status;

}
