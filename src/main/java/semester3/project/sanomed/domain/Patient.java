package semester3.project.sanomed.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Patient{
    private Long id;
    private Integer bsn;
    private Integer healthInsuranceNumber;
    private Person person;
    private List<Diagnose> diagnose;
}
