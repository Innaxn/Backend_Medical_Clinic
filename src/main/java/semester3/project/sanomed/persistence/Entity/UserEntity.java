package semester3.project.sanomed.persistence.Entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Length(max = 100)
    private String password;

    @OneToOne(optional = true)
    @JoinColumn(name = "employee_id")
    private EmployeeEntity emp;

    @OneToOne(optional = true)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<UserRoleEntity> userRoles;
}
