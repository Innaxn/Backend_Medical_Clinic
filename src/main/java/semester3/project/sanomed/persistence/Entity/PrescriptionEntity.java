package semester3.project.sanomed.persistence.Entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "prescription")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PrescriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "emp_id")
    private EmployeeEntity doctor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "diagnose_id")
    private DiagnoseEntity diagnoseEntity;

    @Column(name = "start_date")
    private LocalDate start;

    @Column(name = "end_date")
    private LocalDate end;

    @NotBlank
    @Length(min = 2, max = 50)
    @Column(name = "medication")
    private String medication;

}
