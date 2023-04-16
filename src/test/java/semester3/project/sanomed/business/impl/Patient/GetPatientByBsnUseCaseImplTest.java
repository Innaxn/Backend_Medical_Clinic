package semester3.project.sanomed.business.impl.Patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.domain.Diagnose;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.response.GetPatientsResponse;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.PatientRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPatientByBsnUseCaseImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    GetPatientByBsnUseCaseImpl getPatientByBsnUseCase;

    @Test
    void getPatientByBsn() {
        PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Shar").lastName("Peeters").build();
        Person person = Person.builder().firstName("Shar").lastName("Peeters").build();

        PatientEntity patientEntity = PatientEntity.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).personEmbeddable(personEmbeddable).build();

        when(patientRepository.findByBsnContaining(patientEntity.getBsn().toString()))
                .thenReturn(List.of(patientEntity));

        GetPatientsResponse actual = getPatientByBsnUseCase.getPatientsByBsn(1234);
        Patient expectedp = Patient.builder().id(1L).bsn(1234).healthInsuranceNumber(1234).person(person).build();
        GetPatientsResponse expected = GetPatientsResponse.builder().patients(List.of(expectedp)).build();
        assertEquals(expected, actual);
        verify(patientRepository).findByBsnContaining(String.valueOf(1234));
    }

    @Test
    void getPatient_shouldThrowException() throws InvalidPatientException {
        when(patientRepository.findByBsnContaining("1234"))
                .thenReturn(Collections.emptyList());

        InvalidPatientException thrown = assertThrows(InvalidPatientException.class, () ->{
            getPatientByBsnUseCase.getPatientsByBsn(1234);
        }, "INVALID_PATIENT_ID");

        assertEquals("404 NOT_FOUND \"INVALID_PATIENT_ID\"", thrown.getMessage());
        verify(patientRepository).findByBsnContaining(String.valueOf(1234));
    }
}