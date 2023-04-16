package semester3.project.sanomed.business.impl.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.business.impl.PrescriptionService.CreatePrescriptionUseCaseImpl;
import semester3.project.sanomed.business.validations.DiagnoseIdValidator;
import semester3.project.sanomed.business.validations.EmployeeIdValidator;
import semester3.project.sanomed.business.validations.PatientIdValidator;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.domain.request.CreateAppointmentRequest;
import semester3.project.sanomed.domain.request.CreatePrescriptionRequest;
import semester3.project.sanomed.domain.response.CreateAppointmentResponse;
import semester3.project.sanomed.domain.response.CreatePrescriptionResponse;
import semester3.project.sanomed.persistence.*;
import semester3.project.sanomed.persistence.Entity.*;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateAppointmentUseCaseImplTest {

    @Mock
    AppointmentRepository appointmentRepositoryMock;
    @Mock
    EmployeeRepository employeeRepositoryMock;
    @Mock
    PatientRepository patientRepositoryMock;
    @Mock
    EmployeeIdValidator employeeIdValidatorMock;
    @Mock
    PatientIdValidator patientIdValidatorMock;
    @InjectMocks
    CreateAppointmentUseCaseImpl createAppointmentUseCase;

    @Test
    void createAppointment_shouldBeOk() {
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).build();
        PatientEntity p = PatientEntity.builder().id(1L).build();
        AppointmentEntity appointment = AppointmentEntity.builder().doctor(emp).patient(p).purpose("purpose").build();
        AppointmentEntity expected = AppointmentEntity.builder().id(1L).doctor(emp).patient(p).purpose("purpose").build();

        when(employeeRepositoryMock.findById(emp.getId())).thenReturn(Optional.of(emp));
        when(patientRepositoryMock.findById(p.getId())).thenReturn(Optional.of(p));

        CreateAppointmentRequest request = CreateAppointmentRequest.builder().doctorId(1L).patientId(1L).purpose("purpose").build();
        when(appointmentRepositoryMock.save(appointment)).thenReturn(expected);

        CreateAppointmentResponse response = createAppointmentUseCase.createAppointment(request);


        assertEquals(expected.getId(), response.getId());
        verify(employeeIdValidatorMock, times(1)).validateId(1L);
        verify(patientIdValidatorMock, times(1)).validateId(1L);
        verify(employeeRepositoryMock).findById(emp.getId());
        verify(patientRepositoryMock).findById(p.getId());
        verify(appointmentRepositoryMock).save(appointment);
    }
    @Test
    public void createAppointment_shouldThrowInvalidEmployeeIdException() {
        CreateAppointmentRequest request = CreateAppointmentRequest.builder().doctorId(1L).patientId(1L).purpose("purpose").build();
        when(employeeRepositoryMock.findById(request.getDoctorId()))
                .thenReturn(Optional.empty());

        InvalidEmployeeException thrown = assertThrows(
                InvalidEmployeeException.class,
                () -> createAppointmentUseCase.createAppointment(request)
        );

        assertEquals("404 NOT_FOUND \"INVALID_EMPLOYEE_ID\"", thrown.getMessage());
    }

    @Test
    public void createAppointment_shouldThrowInvalidPatientIdException() {
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).build();
        CreateAppointmentRequest request = CreateAppointmentRequest.builder().doctorId(1L).patientId(1L).purpose("purpose").build();
        when(employeeRepositoryMock.findById(request.getDoctorId()))
                .thenReturn(Optional.of(emp));
        when(patientRepositoryMock.findById(request.getPatientId()))
                .thenReturn(Optional.empty());

        InvalidPatientException thrown = assertThrows(
                InvalidPatientException.class,
                () -> createAppointmentUseCase.createAppointment(request)
        );

        assertEquals("404 NOT_FOUND \"INVALID_PATIENT_ID\"", thrown.getMessage());
        verify(employeeRepositoryMock).findById(emp.getId());
    }

}