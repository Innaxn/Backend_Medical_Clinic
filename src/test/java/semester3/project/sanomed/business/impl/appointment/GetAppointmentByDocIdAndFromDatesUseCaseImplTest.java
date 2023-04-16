package semester3.project.sanomed.business.impl.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.converter.AppointmentConverter;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.UnauthorizedDataAccessException;
import semester3.project.sanomed.business.impl.employee.GetEmployeesByRoleAndStatusUseCaseImpl;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.response.GetAppointmentsResponse;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAppointmentByDocIdAndFromDatesUseCaseImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    GetAppointmentByDocIdAndFromDatesUseCaseImpl getAppointmentByDocIdAndFromDatesUseCase;


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

        when(requestAccessToken.hasRole(RoleEnum.DOCTOR.name())).thenReturn(true);
        when(requestAccessToken.getEmployeeId()).thenReturn(employeeEntity.getId());
        when(appointmentRepository.findAppointmentEntitiesByDoctor_IdAndDateAfter(employeeEntity.getId(), date)).thenReturn(List.of(appointmentEntity));

        GetAppointmentsResponse actual = getAppointmentByDocIdAndFromDatesUseCase.getAppointments(employeeEntity.getId(), date);
        GetAppointmentsResponse response = GetAppointmentsResponse.builder().appointments(List.of(appointment)).build();

        assertEquals(response, actual);
    }

    @Test
    public void testGetAppointments_UnauthorizedAccess() {
        long doctorId = 1L;
        LocalDateTime date = LocalDateTime.now();
        when(requestAccessToken.hasRole(RoleEnum.DOCTOR.name())).thenReturn(true);
        when(requestAccessToken.getEmployeeId()).thenReturn(doctorId + 1);

        try {
            getAppointmentByDocIdAndFromDatesUseCase.getAppointments(doctorId, date);
            fail();
        } catch (UnauthorizedDataAccessException e) {
            assertTrue(e instanceof UnauthorizedDataAccessException);
        }
    }

    @Test
    public void testGetAppointments_NoAppointments() {
        long doctorId = 1L;
        LocalDateTime date = LocalDateTime.now();
        when(requestAccessToken.hasRole(RoleEnum.DOCTOR.name())).thenReturn(true);
        when(requestAccessToken.getEmployeeId()).thenReturn(doctorId);
        when(appointmentRepository.findAppointmentEntitiesByDoctor_IdAndDateAfter(doctorId, date)).thenReturn(Collections.emptyList());

        try {
            getAppointmentByDocIdAndFromDatesUseCase.getAppointments(doctorId, date);
            fail();
        } catch (InvalidAppointmentException e) {
            assertTrue(e instanceof InvalidAppointmentException);
        }
    }

}