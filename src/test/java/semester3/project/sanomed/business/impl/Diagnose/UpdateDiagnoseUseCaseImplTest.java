package semester3.project.sanomed.business.impl.Diagnose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidDiagnoseException;
import semester3.project.sanomed.business.exceptions.InvalidEmployeeException;
import semester3.project.sanomed.domain.request.UpdateDiagnoseRequest;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.EmployeeRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateDiagnoseUseCaseImplTest {
    @Mock
    private  DiagnoseRepository diagnoseRepository;
    @Mock
    private  EmployeeRepository employeeRepository;

    @InjectMocks
    UpdateDiagnoseUseCaseImpl updateDiagnoseUseCase;

    @Test
    void validUpdate() {
        PatientEntity patient =PatientEntity.builder().id(1L).build();
        EmployeeEntity employee = EmployeeEntity.builder().id(1L).build();
        UpdateDiagnoseRequest request = UpdateDiagnoseRequest.builder()
                .id(1L)
                .name("Updated name")
                .description("Updated description")
                .doctorId(2L)
                .build();
        DiagnoseEntity diagnoseEntity = DiagnoseEntity.builder()
                .id(1L)
                .name("Old name")
                .details("Old description")
                .patient(patient)
                .doctor(employee)
                .build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .id(2L)
                .build();
        when(diagnoseRepository.findById(request.getId())).thenReturn(Optional.of(diagnoseEntity));
        when(employeeRepository.findById(request.getDoctorId())).thenReturn(Optional.of(employeeEntity));

        updateDiagnoseUseCase.updateDiagnose(request);

        assertEquals(request.getId(), diagnoseEntity.getId());
        assertEquals(request.getName(), diagnoseEntity.getName());
        assertEquals(request.getDescription(), diagnoseEntity.getDetails());
        assertSame(diagnoseEntity.getPatient(), diagnoseEntity.getPatient());
        assertSame(employeeEntity, diagnoseEntity.getDoctor());
        verify(diagnoseRepository).save(diagnoseEntity);

    }

    @Test
    void testUpdateDiagnose_invalidEmployee() {
        UpdateDiagnoseRequest request = UpdateDiagnoseRequest.builder().id(1L).name("Flu").description("Description").doctorId(2L).build();
        DiagnoseEntity diagnose = new DiagnoseEntity();
        diagnose.setId(1L);
        diagnose.setName("Flu");
        diagnose.setDetails("Description");
        diagnose.setPatient(new PatientEntity());
        diagnose.setDoctor(new EmployeeEntity());
        when(diagnoseRepository.findById(request.getId())).thenReturn(Optional.of(diagnose));
        when(employeeRepository.findById(request.getDoctorId())).thenReturn(Optional.empty());

        assertThrows(InvalidEmployeeException.class, () -> {
            updateDiagnoseUseCase.updateDiagnose(request);
        });
    }

    @Test
    public void testUpdateDiagnose_InvalidDiagnose() {
        UpdateDiagnoseRequest request = UpdateDiagnoseRequest.builder()
                .id(1L)
                .description("updated description")
                .build();

        when(diagnoseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidDiagnoseException.class, () -> updateDiagnoseUseCase.updateDiagnose(request));

        verify(diagnoseRepository).findById(1L);
    }

}