package semester3.project.sanomed.business.impl.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidAppointmentException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.business.exceptions.InvalidPatientException;
import semester3.project.sanomed.domain.request.UpdateAppointmentRequest;
import semester3.project.sanomed.persistence.AppointmentRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.AppointmentEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.PatientRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAppointmentUseCaseImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private  PatientRepository patientRepository;

    @InjectMocks
    UpdateAppointmentUseCaseImpl updateAppointmentUseCase;

    @Test
    void updateAppointment() {
    }

    @Test
    void updateObjectFields() {
        LocalDateTime start = LocalDateTime.of(2022,12,1,10,00,00);
        UpdateAppointmentRequest request = new UpdateAppointmentRequest();
        request.setId(1L);
        request.setStartDate(start);
        request.setPurpose("Test Purpose");
        request.setDocId(2);
        request.setPatientid(3);

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .id(1L)
                .date(start)
                .purpose("Consultation")
                .build();

        when(appointmentRepository.findById(request.getId())).thenReturn(Optional.of(appointmentEntity));

        EmployeeEntity mockEmployeeEntity = EmployeeEntity.builder().id(2L).build();

        when(employeeRepository.findById(request.getDocId())).thenReturn(Optional.of(mockEmployeeEntity));

        PatientEntity mockPatientEntity = PatientEntity.builder().id(1L).build();

        when(patientRepository.findById(request.getPatientid())).thenReturn(Optional.of(mockPatientEntity));

        updateAppointmentUseCase.updateAppointment(request);

        verify(appointmentRepository).save(appointmentEntity);
        assertEquals(request.getId(), appointmentEntity.getId());
        assertEquals(request.getStartDate(), appointmentEntity.getDate());
        assertEquals(request.getPurpose(), appointmentEntity.getPurpose());
        assertEquals(mockEmployeeEntity, appointmentEntity.getDoctor());
        assertEquals(mockPatientEntity, appointmentEntity.getPatient());
    }
    @Test
    public void testUpdateAppointmentWithInvalidDocId() {
        LocalDateTime start = LocalDateTime.of(2022,12,1,10,00,00);
        UpdateAppointmentRequest request = UpdateAppointmentRequest.builder().id(1L).docId(2).build();

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .id(1L)
                .date(start)
                .purpose("Consultation")
                .build();

        when(appointmentRepository.findById(request.getId())).thenReturn(Optional.of(appointmentEntity));

        when(employeeRepository.findById(request.getDocId())).thenReturn(Optional.empty());

        assertThrows(InvalidEmployeeException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }

    @Test
    public void testUpdateAppointmentWithInvalidPatientId() {
        LocalDateTime start = LocalDateTime.of(2022,12,1,10,00,00);
        EmployeeEntity emp = EmployeeEntity.builder().id(2L).build();

        UpdateAppointmentRequest request = UpdateAppointmentRequest.builder().id(1L).docId(2).patientid(2).build();

        AppointmentEntity appointmentEntity = AppointmentEntity.builder()
                .id(1L)
                .date(start)
                .purpose("Consultation")
                .build();

        when(appointmentRepository.findById(request.getId())).thenReturn(Optional.of(appointmentEntity));
        when(employeeRepository.findById(request.getDocId())).thenReturn(Optional.of(emp));
        when(patientRepository.findById(request.getPatientid())).thenReturn(Optional.empty());

        assertThrows(InvalidPatientException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }

    @Test
    public void testUpdateAppointmentWithInvalidAppointmentId() {

        UpdateAppointmentRequest request = UpdateAppointmentRequest.builder().id(1L).build();

        when(appointmentRepository.findById(request.getId())).thenReturn(Optional.empty());

        assertThrows(InvalidAppointmentException.class, () -> updateAppointmentUseCase.updateAppointment(request));
    }


}