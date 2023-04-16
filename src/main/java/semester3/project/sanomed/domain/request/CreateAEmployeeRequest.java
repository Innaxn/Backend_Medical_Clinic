package semester3.project.sanomed.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import semester3.project.sanomed.persistence.Entity.RoleEnum;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAEmployeeRequest {
   // @NotNull
  //  @Min(1)
   // private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private LocalDate birthdate;

    private String phoneNumber;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String description;

    private RoleEnum role;

}
