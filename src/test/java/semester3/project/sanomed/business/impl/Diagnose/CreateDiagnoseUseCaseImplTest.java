package semester3.project.sanomed.business.impl.Diagnose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.business.validations.EmployeeIdValidator;
import semester3.project.sanomed.business.validations.PatientIdValidator;
import semester3.project.sanomed.domain.request.CreateDiagnosisRequest;
import semester3.project.sanomed.domain.response.CreateDiagnoseResponse;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.PatientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateDiagnoseUseCaseImplTest {

    @Mock
    DiagnoseRepository diagnoseRepositoryMock;
    @Mock
    PatientRepository patientRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmployeeIdValidator employeeIdValidator;
    @Mock
    PatientIdValidator patientIdValidator;
    @InjectMocks
    CreateDiagnoseUseCaseImpl createDiagnoseUseCase;

    @Test
    void createDiagnose() {
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).build();
        PatientEntity p = PatientEntity.builder().id(1L).build();
        DiagnoseEntity d = DiagnoseEntity.builder().name("test").details("test").patient(p).doctor(emp).build();
        DiagnoseEntity expected = DiagnoseEntity.builder().id(1L).name("test").details("test").patient(p).doctor(emp).build();

        when(diagnoseRepositoryMock.save(d))
                .thenReturn(d);
        when(employeeRepository.findById(emp.getId())).thenReturn(Optional.of(emp));
        when(patientRepository.findById(p.getId())).thenReturn(Optional.of(p));

        CreateDiagnosisRequest request = CreateDiagnosisRequest.builder().doctorId(1L).patientId(1L).name("test").details("test").build();
        when(diagnoseRepositoryMock.save(d)).thenReturn(expected);

        CreateDiagnoseResponse response = createDiagnoseUseCase.createDiagnose(request);

        assertEquals(expected.getId(), response.getId());
        verify(employeeIdValidator, times(1)).validateId(1L);
        verify(patientIdValidator, times(1)).validateId(1L);
        verify(employeeRepository).findById(emp.getId());
        verify(patientRepository).findById(p.getId());
        verify(diagnoseRepositoryMock).save(d);
    }

    @Test
    void createDiagnose_shouldThrowInvalidEmployeeException() {
        CreateDiagnosisRequest request = CreateDiagnosisRequest.builder().doctorId(1L).patientId(1L).name("test").details("test").build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        InvalidEmployeeException thrown = assertThrows(InvalidEmployeeException.class, () ->{
            createDiagnoseUseCase.createDiagnose(request);
        }, "Invalid ID, does not exist");

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());

        assertThrows(InvalidEmployeeException.class, () -> {
            createDiagnoseUseCase.createDiagnose(request);
        }, "INVALID_EMPLOYEE_ID");
    }
    @Test
    void createDiagnose_shouldThrowInvalidPatientException() {
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).build();
        CreateDiagnosisRequest request = CreateDiagnosisRequest.builder().doctorId(1L).patientId(1L).name("test").details("test").build();
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());
        InvalidPatientException thrown = assertThrows(InvalidPatientException.class, () ->{
            createDiagnoseUseCase.createDiagnose(request);
        }, "Invalid ID, does not exist");

        assertEquals("404 NOT_FOUND \"INVALID_PATIENT_ID\"", thrown.getMessage());

        assertThrows(InvalidPatientException.class, () -> {
            createDiagnoseUseCase.createDiagnose(request);
        }, "INVALID_PATIENT_ID");
    }
}