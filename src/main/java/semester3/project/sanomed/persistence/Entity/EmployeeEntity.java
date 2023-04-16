package semester3.project.sanomed.persistence.Entity;

import lombok.*;
import semester3.project.sanomed.domain.Status_Employee;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class EmployeeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name_person")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name_person")),
            @AttributeOverride(name = "email", column = @Column(name = "email_person")),
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number_person")),
            @AttributeOverride(name = "birthdate", column = @Column(name = "birthdate_person"))
    })
    private PersonEmbeddable personEmbeddable;


    @Column(name = "description")
    private String description;

//    @Enumerated(value = EnumType.STRING)
//    @Column(name = "employee_role")
//    private EmployeeRole role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "employee_status")
    private Status_Employee status;

//    @OneToOne(optional = true)
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
}
