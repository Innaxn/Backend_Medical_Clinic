package semester3.project.sanomed.business.impl.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;
import semester3.project.sanomed.business.converter.PersonConverter;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.impl.employee.GetEmployeeUseCaseImpl;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.response.GetPatientsResponse;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.*;
import semester3.project.sanomed.persistence.PatientRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPatientByIdUseCaseImplTest {

    @Mock
    PatientRepository patientRepository;
    @Mock
    AccessToken requestAccessToken;
    @InjectMocks
    GetPatientByIdUseCaseImpl getPatientByIdUseCase;

    @Test
    void getPatientById() {
        PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Shar").lastName("Peeters").build();
        Person person = Person.builder().firstName("Shar").lastName("Peeters").build();

        PatientEntity patientD = PatientEntity.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).personEmbeddable(personEmbeddable).build();
        Patient pD = Patient.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).person(person).build();

        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).personEmbeddable(personEmbeddable).build();
        Employee employee = Employee.builder().id(1L).person(person).build();

        DiagnoseEntity diagnoseEntity = DiagnoseEntity.builder().id(1L).patient(patientD).doctor(employeeEntity).build();
        Diagnose diagnose = Diagnose.builder().id(1L).patient(pD).doctor(employee).build();

        PatientEntity patientEntity = PatientEntity.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).personEmbeddable(personEmbeddable).diagnosis(List.of(diagnoseEntity)).build();
        when(requestAccessToken.hasRole(any())).thenReturn(true);
        when(requestAccessToken.getPatientId()).thenReturn(patientD.getId());
        when(patientRepository.findById(patientEntity.getId()))
                .thenReturn(Optional.of(patientEntity));


        Patient actual = getPatientByIdUseCase.getPatientById(1L);
        Patient expected = Patient.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).person(person).diagnose(List.of(diagnose)).build();
        assertEquals(expected, actual);
        verify(patientRepository).findById(1L);
        verify(requestAccessToken).getPatientId();
    }

    @Test
    void getPatientById_shouldThrowException() throws InvalidPatientException {

        InvalidPatientException thrown = assertThrows(InvalidPatientException.class, () ->{
            getPatientByIdUseCase.getPatientById(1L);
        }, "INVALID_PATIENT_ID");

        assertEquals("404 NOT_FOUND \"INVALID_PATIENT_ID\"", thrown.getMessage());

    }

    @Test
    void getPatientById_shouldThrowUnauthorizedDataAccessException() throws UnauthorizedDataAccessException {

        when(requestAccessToken.hasRole(any())).thenReturn(true);

        UnauthorizedDataAccessException thrown = assertThrows(UnauthorizedDataAccessException.class, () ->{
            getPatientByIdUseCase.getPatientById(1L);
        }, "ID_NOT_FROM_LOGGED_IN_USER");

        assertEquals("400 BAD_REQUEST \"ID_NOT_FROM_LOGGED_IN_USER\"", thrown.getMessage());

    }
}