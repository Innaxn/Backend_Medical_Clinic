package semester3.project.sanomed.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPaswordRequest {
    private Long id;
    private String currentPassword;
    private String newPassword;
    private RoleEnum role;
}
