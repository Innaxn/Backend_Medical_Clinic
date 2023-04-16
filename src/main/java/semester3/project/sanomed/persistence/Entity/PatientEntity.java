package semester3.project.sanomed.persistence.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PatientEntity {
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

    @Column(name = "bsn")
    private Integer bsn;

    @Column(name = "health_ins_num")
    private Integer healthInsuranceNumber;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<DiagnoseEntity> diagnosis;

//    @OneToOne(optional = true)
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
}