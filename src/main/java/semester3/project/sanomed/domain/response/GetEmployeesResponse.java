package semester3.project.sanomed.domain.response;

import lombok.Builder;
import lombok.Data;
import semester3.project.sanomed.domain.Employee;

import java.util.List;

@Data
@Builder
public class GetEmployeesResponse {
    private List<Employee> employees;

   // private List<EmployeeData> employees;

//    @Data
//    @Builder
//    public static class EmployeeData {
//        private String id;
//        private String name;
//    }
}
