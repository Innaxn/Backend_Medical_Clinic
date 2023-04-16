package semester3.project.sanomed.business.impl.PrescriptionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.validations.DiagnoseIdValidator;
import semester3.project.sanomed.business.validations.EmployeeIdValidator;
import semester3.project.sanomed.domain.request.CreatePrescriptionRequest;
import semester3.project.sanomed.domain.response.CreatePrescriptionResponse;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PrescriptionEntity;
import semester3.project.sanomed.persistence.PrescriptionRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePrescriptionUseCaseImplTest {

    @Mock
    EmployeeRepository employeeRepositoryMock;
    @Mock
    DiagnoseRepository diagnoseRepositoryMock;
    @Mock
    PrescriptionRepository prescriptionRepositoryMock;
    @Mock
    EmployeeIdValidator employeeIdValidatorMock;
    @Mock
    DiagnoseIdValidator diagnoseIdValidatorMock;
    @InjectMocks
    CreatePrescriptionUseCaseImpl createPrescriptionUseCase;

    @Test
    void createPrescription() {
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).build();
        PatientEntity p = PatientEntity.builder().id(1L).build();
        DiagnoseEntity d = DiagnoseEntity.builder().id(1L).name("test").details("test").patient(p).doctor(emp).build();
        PrescriptionEntity prescription = PrescriptionEntity.builder().doctor(emp).diagnoseEntity(d).build();
        PrescriptionEntity expected = PrescriptionEntity.builder().id(1L).doctor(emp).diagnoseEntity(d).build();

        when(employeeRepositoryMock.findById(emp.getId())).thenReturn(Optional.of(emp));
        when(diagnoseRepositoryMock.findById(d.getId())).thenReturn(Optional.of(d));


        CreatePrescriptionRequest request = CreatePrescriptionRequest.builder().doctorId(1L).diagnoseId(1L).build();
        when(prescriptionRepositoryMock.save(prescription)).thenReturn(expected);

        CreatePrescriptionResponse response = createPrescriptionUseCase.createPrescription(request);

        assertEquals(expected.getId(), response.getId());
        verify(employeeIdValidatorMock, times(1)).validateId(1L);
        verify(diagnoseIdValidatorMock, times(1)).validateId(1L);
        verify(employeeRepositoryMock).findById(emp.getId());
        verify(diagnoseRepositoryMock).findById(d.getId());
        verify(prescriptionRepositoryMock).save(prescription);

    }

    @Test
    void createPrescription_shouldThrowInvalidEmployeeException() {
        CreatePrescriptionRequest request = CreatePrescriptionRequest.builder().doctorId(1L).diagnoseId(1L).start(LocalDate.of(2022, 10,1))
                .end(LocalDate.of(2023, 01, 1)).medication("test").build();
        when(employeeRepositoryMock.findById(request.getDoctorId())).thenReturn(Optional.empty());
        InvalidEmployeeException thrown = assertThrows(InvalidEmployeeException.class, () -> {
            createPrescriptionUseCase.createPrescription(request);
        }, "INVALID_EMPLOYEE_ID");

        assertEquals("INVALID_EMPLOYEE_ID", thrown.getReason());
        verify(employeeRepositoryMock).findById(request.getDoctorId());
    }

    @Test
    void createPrescription_shouldThrowInvalidDiagnoseException() {
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).build();
        CreatePrescriptionRequest request = CreatePrescriptionRequest.builder().doctorId(1L).diagnoseId(1L).build();
        when(employeeRepositoryMock.findById(request.getDoctorId())).thenReturn(Optional.of(emp));
        when(diagnoseRepositoryMock.findById(request.getDiagnoseId())).thenReturn(Optional.empty());

        InvalidDiagnoseException thrown = assertThrows(InvalidDiagnoseException.class, () -> {
            createPrescriptionUseCase.createPrescription(request);
        }, "INVALID_DIAGNOSE_ID");

        assertEquals("INVALID_DIAGNOSE_ID", thrown.getReason());
        verify(employeeRepositoryMock).findById(request.getDoctorId());
        verify(diagnoseRepositoryMock).findById(request.getDiagnoseId());
    }
}