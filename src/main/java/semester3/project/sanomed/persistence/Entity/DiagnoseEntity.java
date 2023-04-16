package semester3.project.sanomed.persistence.Entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "diagnose")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiagnoseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "name_diagnose")
    private String name;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "description")
    private String details;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private EmployeeEntity doctor;
}
