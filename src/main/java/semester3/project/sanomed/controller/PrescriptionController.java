package semester3.project.sanomed.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.project.sanomed.business.interfaces.prescription.*;
import semester3.project.sanomed.configuration.security.isauthenticated.IsAuthenticated;
import semester3.project.sanomed.domain.request.CreatePrescriptionRequest;
import semester3.project.sanomed.domain.request.UpdatePrescriptionRequest;
import semester3.project.sanomed.domain.response.CreatePrescriptionResponse;
import semester3.project.sanomed.domain.response.GetPrescriptionsResponse;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/prescriptions")
@AllArgsConstructor
public class PrescriptionController {
    private final CreatePrescriptionUseCase createPrescriptionUseCase;
    private final DeletePrescriptionUseCase deletePrescriptionUseCase;
    private final UpdatePrescriptionUseCase updatePrescriptionUseCase;
    private final GetPrescriptionsByDiagnoseIdUseCase getPrescriptionsByDiagnoseIdUseCase;

    @IsAuthenticated
    @RolesAllowed({"DOCTOR"})
    @PostMapping()
    public ResponseEntity<CreatePrescriptionResponse> create(@RequestBody CreatePrescriptionRequest request){
      CreatePrescriptionResponse response = createPrescriptionUseCase.createPrescription(request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @IsAuthenticated
    @RolesAllowed({"DOCTOR"})
    @DeleteMapping("delete/{prescriptionId}")
    public ResponseEntity<DeletePrescriptionUseCase> deletePrescription(@PathVariable long prescriptionId){
        deletePrescriptionUseCase.deletePrescription(prescriptionId);
        return ResponseEntity.noContent().build();
    }
    @IsAuthenticated
    @RolesAllowed({"DOCTOR"})
    @PutMapping("{id}")
    public ResponseEntity<Void> updatePrescription(@PathVariable("id") long id,
                                               @RequestBody @Valid UpdatePrescriptionRequest request) {
        request.setId(id);
        updatePrescriptionUseCase.updatePrescription(request);
        return ResponseEntity.noContent().build();
    }
    @IsAuthenticated
    @RolesAllowed({"DOCTOR", "PATIENT"})
    @GetMapping("getByDId/{dId}")
    public ResponseEntity<GetPrescriptionsResponse> getPrescriptionsByDiagnoseId(@PathVariable(value = "dId")long dId)
    {
        GetPrescriptionsResponse response = getPrescriptionsByDiagnoseIdUseCase.getPrescriptionsByDiagnoseId(dId);
        return ResponseEntity.ok(response);
    }

}

