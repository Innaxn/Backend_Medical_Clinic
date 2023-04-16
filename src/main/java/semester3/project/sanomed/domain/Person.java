package semester3.project.sanomed.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthdate;

}
