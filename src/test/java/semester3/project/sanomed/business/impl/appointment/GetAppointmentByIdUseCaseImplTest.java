package semester3.project.sanomed.business.impl.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.business.impl.Diagnose.GetDiagnoseUseCaseImpl;
import semester3.project.sanomed.domain.*;
import semester3.project.sanomed.domain.request.CreateAppointmentRequest;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAppointmentByIdUseCaseImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private GetAppointmentByIdUseCaseImpl getAppointmentByIdUseCase;

    @Test
    void getAppointment() {
        PersonEmbeddable oneP = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").phoneNumber("0898939914").birthdate(LocalDate.of(1980,10,1))
                .email( "nrdelkova@gmail.com").build();
        Person person = Person.builder().firstName("Ivana").lastName("Nedelkova").phoneNumber("0898939914").birthdate(LocalDate.of(1980,10,1))
                .email( "nrdelkova@gmail.com").build();

        EmployeeEntity emp = EmployeeEntity.builder().id(1L).personEmbeddable(oneP).build();
        Employee employee = Employee.builder().id(1L).person(person).build();

        PatientEntity p = PatientEntity.builder().id(1L).personEmbeddable(oneP).build();
        Patient patient = Patient.builder().id(1L).person(person).build();

        AppointmentEntity appointment = AppointmentEntity.builder().id(1L).patient(p).doctor(emp).purpose("purpose").build();

        when(appointmentRepository.findById(1L))
                .thenReturn(Optional.of(appointment));

        Appointment actualResult = getAppointmentByIdUseCase.getAppointment(1L);

        Appointment expected = Appointment.builder().id(1L).patient(patient).doctor(employee).purpose("purpose").build();

        assertEquals(expected, actualResult);
        verify(appointmentRepository).findById(1L);
    }

    @Test
    public void getAppointment_shouldThrowInvalidAppointmentException() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());
        InvalidAppointmentException thrown = assertThrows(InvalidAppointmentException.class, () ->{
            getAppointmentByIdUseCase.getAppointment(1L);
        }, "Invalid ID, does not exist");

        assertEquals("404 NOT_FOUND \"Invalid ID, does not exist\"", thrown.getMessage());

        assertThrows(InvalidAppointmentException.class, () -> {
            getAppointmentByIdUseCase.getAppointment(1L);
        }, "Invalid ID, does not exist");
    }

}