package semester3.project.sanomed.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.project.sanomed.business.interfaces.diagnose.*;
import semester3.project.sanomed.configuration.security.isauthenticated.IsAuthenticated;
import semester3.project.sanomed.domain.request.UpdateDiagnoseRequest;
import semester3.project.sanomed.domain.response.CreateDiagnoseResponse;
import semester3.project.sanomed.domain.request.CreateDiagnosisRequest;
import semester3.project.sanomed.domain.Diagnose;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/diagnosis")
@AllArgsConstructor
public class DiagnosisController {
    private final CreateDiagnoseUseCase createDiagnoseUseCase;
    private final DeleteDiagnosisUseCase deleteDiagnoseUseCase;
    private final GetDiagnoseUseCase getDiagnoseUseCase;
    private final GetDiagnosisByPatientIdUseCase getDiagnosisByPatientIdUseCase;
    private final UpdateDiagnoseUseCase updateDiagnoseUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_DOCTOR"})
    @PostMapping
    public ResponseEntity<CreateDiagnoseResponse> create (@RequestBody @Valid CreateDiagnosisRequest request){
        CreateDiagnoseResponse response = createDiagnoseUseCase.createDiagnose(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DOCTOR"})
    @DeleteMapping("delete/{diagnosisId}")
    public ResponseEntity<DeleteDiagnosisUseCase> deleteDiagnosis(@PathVariable long diagnosisId){
        deleteDiagnoseUseCase.deleteDiagnose(diagnosisId);
        return ResponseEntity.noContent().build();
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DOCTOR", "ROLE_PATIENT"})
    @GetMapping("get/{id}")
    public ResponseEntity<DiagnoseData> getDiagnose(@PathVariable(value = "id") final long id) {
        final Optional<DiagnoseData> optional = getDiagnoseUseCase.getDiagnose(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(optional.get());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_DOCTOR", "ROLE_PATIENT"})
    @GetMapping("getByPId/{pId}")
    public ResponseEntity<GetDiagnosisResponse> getDiagnosisByPatientId(@PathVariable(value = "pId")long pId)
    {
        GetDiagnosisResponse response = getDiagnosisByPatientIdUseCase.getDiagnosisByPatientId(pId);
        return ResponseEntity.ok(response);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_DOCTOR"})
    @PutMapping("{id}")
    public ResponseEntity<Void> updateDiagnose(@PathVariable("id") long id,
                                                   @RequestBody @Valid UpdateDiagnoseRequest request) {
        request.setId(id);
        updateDiagnoseUseCase.updateDiagnose(request);
        return ResponseEntity.noContent().build();
    }
}
