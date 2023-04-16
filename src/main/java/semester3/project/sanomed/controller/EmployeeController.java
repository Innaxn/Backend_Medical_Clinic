package semester3.project.sanomed.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.project.sanomed.business.interfaces.employee.*;
import semester3.project.sanomed.configuration.security.isauthenticated.IsAuthenticated;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.request.CreateAEmployeeRequest;
import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;
import semester3.project.sanomed.domain.request.UpdateStatusEmployeeRequest;
import semester3.project.sanomed.domain.response.CreateEmployeeResponse;
import semester3.project.sanomed.domain.response.GeneralStatisticsResponse;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.persistence.Entity.RoleEnum;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetEmployeesUseCase getAllEmployeesUseCase;
    private final GetEmployeeUseCase getEmployeeUseCase;
    private final GetEmployeeByLastNameUseCase getEmployeeByLastNameUseCase;
    private final UpdateEmployeeUseCase updateEmployeeUseCase;
    private final GetEmployeesByRole getEmployeesByRole;
    private final GetEmployeesByRoleAndStatusUseCase getEmployeesByRoleAndStatus;
    private final UpdateEmployeeStatusUseCase updateEmployeeStatus;
    private final GetFirstFiveUseCase getFirstFiveUseCase;
    private final GetDoctorByIdStatusPublicUseCase getDoctorByIdStatusPublic;
    private final GetTopFiveEmployeesForTheMonthUseCase getTopFiveEmployeesForTheMonthUseCase;
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    @PostMapping()
    public ResponseEntity<CreateEmployeeResponse> createEmployee(@RequestBody @Valid CreateAEmployeeRequest request) {
        CreateEmployeeResponse response = createEmployeeUseCase.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR","ROLE_DOCTOR", "ROLE_SECRETARY"})
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(value = "id") final long id) {
        final Employee employee = getEmployeeUseCase.getEmployee(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(employee);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    @GetMapping("role")
    public ResponseEntity<GetEmployeesResponse> getEmployeesByRole(@RequestParam("role") String role) {
        final GetEmployeesResponse employee = getEmployeesByRole.getEmployees(RoleEnum.valueOf(role));
        return ResponseEntity.ok(employee);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    @GetMapping
    public ResponseEntity<GetEmployeesResponse> getAllEmployees() {
        return ResponseEntity.ok(getAllEmployeesUseCase.getEmployees());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    @GetMapping("/getfive")
    public ResponseEntity<GetEmployeesResponse> getFiveEmployees() {
        return ResponseEntity.ok(getFirstFiveUseCase.getEmployeesFive());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR","ROLE_DOCTOR", "ROLE_SECRETARY"})
    @PutMapping("{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable("id") long id, @RequestBody @Valid UpdateBasicEmployeeRequest request){
        request.setId(id);
        updateEmployeeUseCase.updateEmployee(request);
        return ResponseEntity.noContent().build();

    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    @PutMapping("status/{id}")
    public ResponseEntity<Void> updateEmployeeStatus(@PathVariable("id") long id, @RequestBody @Valid UpdateStatusEmployeeRequest request){
        request.setId(id);
        updateEmployeeStatus.updateEmployeeStatus(request);
        return ResponseEntity.noContent().build();
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    @GetMapping("/topfive")
    public List<GeneralStatisticsResponse> getTopFive() {
        return getTopFiveEmployeesForTheMonthUseCase.getStatistics();
    }

    @GetMapping("lastName")
    public ResponseEntity<GetEmployeesResponse> getDoctorsByLastName(@RequestParam("ln") String lastName) {
        return ResponseEntity.ok(getEmployeeByLastNameUseCase.getEmployeeByLastName(lastName));
    }

    @GetMapping("roleEmp/status")
    public ResponseEntity<GetEmployeesResponse> getEmployeesByRoleAndStatus(@RequestParam("roleEmp") String role, @RequestParam("status") String status) {
        final GetEmployeesResponse employee = getEmployeesByRoleAndStatus.getEmployeesByRoleAndStatus(RoleEnum.valueOf(role), Status_Employee.valueOf(status));
        return ResponseEntity.ok(employee);
    }
    @GetMapping("doctors/{id}")
    public ResponseEntity<Employee> getDoctorByIdRoleAndStatus(@PathVariable(value = "id") final long id) {
        final Employee employee = getDoctorByIdStatusPublic.getEmployee(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(employee);
    }

}
