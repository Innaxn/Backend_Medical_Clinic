package semester3.project.sanomed.persistence.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_patient")
    private PatientEntity patient;

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_doctor")
    private EmployeeEntity doctor;

    @Column(name = "date_appointment")
    private LocalDateTime date;

    @Column(name = "purpose")
    private String purpose;
}
