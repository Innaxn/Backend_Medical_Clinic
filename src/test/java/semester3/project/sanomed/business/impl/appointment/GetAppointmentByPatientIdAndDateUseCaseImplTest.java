package semester3.project.sanomed.business.impl.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.impl.employee.GetEmployeesByRoleAndStatusUseCaseImpl;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.response.GetAppointmentsResponse;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAppointmentByPatientIdAndDateUseCaseImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    GetAppointmentByPatientIdAndDateUseCaseImpl getAppointmentByPatientIdAndDateUseCase;

    @Test
    public void testGetAppointments() {
        PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Ivana").firstName("Nedelkova").email("email@email.com").build();
        Person person =  Person.builder().firstName("Ivana").firstName("Nedelkova").email("email@email.com").build();
        Patient patient = Patient.builder().id(1L).person(person).build();
        PatientEntity patientEntity = PatientEntity.builder().id(1L).personEmbeddable(personEmbeddable).build();
        Employee employee = Employee.builder().id(1L).person(person).build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).personEmbeddable(personEmbeddable).build();
        LocalDateTime date = LocalDateTime.now();
        AppointmentEntity appointmentEntity = AppointmentEntity.builder().id(1L).doctor(employeeEntity).patient(patientEntity).date(date).build();
        Appointment appointment = Appointment.builder().id(1L).doctor(employee).patient(patient).start(date).build();

        when(requestAccessToken.hasRole(RoleEnum.PATIENT.name())).thenReturn(true);
        when(requestAccessToken.getPatientId()).thenReturn(patientEntity.getId());
        when(appointmentRepository.findAppointmentEntitiesByPatient_IdAndDateAfter(patientEntity.getId(), date)).thenReturn(List.of(appointmentEntity));

        GetAppointmentsResponse actual = getAppointmentByPatientIdAndDateUseCase.getAppointments(patientEntity.getId(), date);
        GetAppointmentsResponse response = GetAppointmentsResponse.builder().appointments(List.of(appointment)).build();

        assertEquals(response, actual);
        verify(appointmentRepository).findAppointmentEntitiesByPatient_IdAndDateAfter(patientEntity.getId(), date);
    }

    @Test
    public void testGetAppointments_UnauthorizedAccess() {
        long patientId = 1L;
        LocalDateTime date = LocalDateTime.now();
        when(requestAccessToken.hasRole(RoleEnum.PATIENT.name())).thenReturn(true);
        when(requestAccessToken.getPatientId()).thenReturn(patientId + 1);

        try {
            getAppointmentByPatientIdAndDateUseCase.getAppointments(patientId, date);
            fail();
        } catch (UnauthorizedDataAccessException e) {
            assertTrue(e instanceof UnauthorizedDataAccessException);
        }
    }

    @Test
    public void testGetAppointments_NoAppointments() {
        long patientId = 1L;
        LocalDateTime date = LocalDateTime.now();
        when(requestAccessToken.hasRole(RoleEnum.PATIENT.name())).thenReturn(true);
        when(requestAccessToken.getPatientId()).thenReturn(patientId);
        when(appointmentRepository.findAppointmentEntitiesByPatient_IdAndDateAfter(patientId, date)).thenReturn(Collections.emptyList());

        try {
            getAppointmentByPatientIdAndDateUseCase.getAppointments(patientId, date);
            fail();
        } catch (InvalidAppointmentException e) {
            assertTrue(e instanceof InvalidAppointmentException);
        }
        verify(appointmentRepository).findAppointmentEntitiesByPatient_IdAndDateAfter(patientId, date);

    }
}