package semester3.project.sanomed.controller;


import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semester3.project.sanomed.business.interfaces.appointment.*;
import semester3.project.sanomed.configuration.security.isauthenticated.IsAuthenticated;
import semester3.project.sanomed.domain.Appointment;
import semester3.project.sanomed.domain.request.CreateAppointmentRequest;
import semester3.project.sanomed.domain.request.UpdateAppointmentRequest;
import semester3.project.sanomed.domain.response.CreateAppointmentResponse;
import semester3.project.sanomed.domain.response.GetAppointmentsResponse;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final DeleteAppointmentUseCase deleteAppointmentUseCase;
    private final GetAppointmentByIdUseCase getAppointmentByIdUseCase;
    private final GetAppointmentByDocIdAndFromDatesUseCase getAppointmentByDocIdAndFromDatesUseCase;
    private final GetAppointmentByPatientIdAndDateUseCase getAppointmentByPatientIdAndDateUseCase;
    private final UpdateAppointmentUseCase updateAppointmentUseCase;


    @IsAuthenticated
    @RolesAllowed({"ROLE_PATIENT", "ROLE_SECRETARY"})
    @PostMapping()
    public ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody @Valid CreateAppointmentRequest request) {
        CreateAppointmentResponse response = createAppointmentUseCase.createAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @IsAuthenticated
    @RolesAllowed({"SECRETARY"})
    @DeleteMapping("delete/{id}")
    public ResponseEntity<DeleteAppointmentUseCase> deleteAppointment(@PathVariable long id){
        deleteAppointmentUseCase.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @IsAuthenticated
    @RolesAllowed({"DOCTOR"})
    @GetMapping("/doc/{dId}/date")
    public ResponseEntity<GetAppointmentsResponse> getAppointmentByDocIdAndDate(@PathVariable(value = "dId") final long id, @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        final GetAppointmentsResponse response = getAppointmentByDocIdAndFromDatesUseCase.getAppointments(id, date);
        return ResponseEntity.ok(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_PATIENT", "ROLE_SECRETARY"})
    @GetMapping("/patient/{pId}/date")
    public ResponseEntity<GetAppointmentsResponse> getAppointmentByPatientIdAndDate(@PathVariable(value = "pId") final long id, @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        final GetAppointmentsResponse response = getAppointmentByPatientIdAndDateUseCase.getAppointments(id, date);
        return ResponseEntity.ok(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_SECRETARY"})
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable(value = "id") final long id) {
        final Appointment appointment = getAppointmentByIdUseCase.getAppointment(id);
        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(appointment);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_SECRETARY"})
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateAppointment(@PathVariable("id") long id, @RequestBody @Valid UpdateAppointmentRequest request){
        request.setId(id);
        updateAppointmentUseCase.updateAppointment(request);
        return ResponseEntity.noContent().build();

    }
}
