package semester3.project.sanomed.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.project.sanomed.business.interfaces.employee.GetEmployeesUseCase;
import semester3.project.sanomed.business.interfaces.patient.*;
import semester3.project.sanomed.configuration.security.isauthenticated.IsAuthenticated;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.request.CreatePatientRequest;
import semester3.project.sanomed.domain.request.UpdateBasicEmployeeRequest;
import semester3.project.sanomed.domain.request.UpdateBasicPatientRequest;
import semester3.project.sanomed.domain.response.CreatePatientResponse;
import semester3.project.sanomed.domain.response.GeneralStatisticsResponse;
import semester3.project.sanomed.domain.response.GetEmployeesResponse;
import semester3.project.sanomed.domain.response.GetPatientsResponse;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {
    private final CreatePatientUseCase createPatientUseCase;
    private final DeletePatientUseCase deletePatientUseCase;
    private final GetPatientByIdUseCase getPatientByIdUseCase;
    private final GetPatientByBsnUseCase getPatientByBsnUseCase;
    private final UpdatePatientUseCase updatePatientUseCase;
    private final GetPatientsUseCase getPatientsUseCase;
    private final GetTopFivePatientUseCase getTopFivePatientUseCase;

    @PostMapping()
    public ResponseEntity<CreatePatientResponse> createPatient(@RequestBody @Valid CreatePatientRequest request) {
        CreatePatientResponse response = createPatientUseCase.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @IsAuthenticated
    @RolesAllowed({"DOCTOR", "ROLE_SECRETARY"})
    @GetMapping
    public ResponseEntity<GetPatientsResponse> getPatients() {
        return ResponseEntity.ok(getPatientsUseCase.getPatients());
    }
    @DeleteMapping("delete/{patientId}")
    public ResponseEntity<DeletePatientUseCase> deletePatient(@PathVariable long patientId){
        deletePatientUseCase.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
    @IsAuthenticated
    @RolesAllowed({"DOCTOR", "SECRETARY", "PATIENT"})
    @GetMapping("get/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") long id){
        final Patient patient  = getPatientByIdUseCase.getPatientById(id);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(patient);
    }
    @IsAuthenticated
    @RolesAllowed({"DOCTOR", "SECRETARY"})
    @GetMapping("getByBsn{bsn}")
    public ResponseEntity<GetPatientsResponse> getPatientByBsn(@RequestParam(value = "bsn") Integer bsn) {
        final GetPatientsResponse patient = getPatientByBsnUseCase.getPatientsByBsn(bsn);
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(patient);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMINISTRATOR"})
    @GetMapping("/topfive")
    public List<GeneralStatisticsResponse> getTopFive() {
        return getTopFivePatientUseCase.getStatistics();
    }

    @IsAuthenticated
    @RolesAllowed({"PATIENT"})
    @PutMapping("{id}")
    public ResponseEntity<Void> updatePatient(@PathVariable("id") long id, @RequestBody @Valid UpdateBasicPatientRequest request){
        request.setId(id);
        updatePatientUseCase.updatePatient(request);
        return ResponseEntity.noContent().build();
    }


}
