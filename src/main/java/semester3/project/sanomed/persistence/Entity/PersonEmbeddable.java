package semester3.project.sanomed.persistence.Entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Embeddable
@EqualsAndHashCode
public class PersonEmbeddable {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthdate;
}
