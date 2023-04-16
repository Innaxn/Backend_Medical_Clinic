package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeneralStatisticsResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer count;


}
