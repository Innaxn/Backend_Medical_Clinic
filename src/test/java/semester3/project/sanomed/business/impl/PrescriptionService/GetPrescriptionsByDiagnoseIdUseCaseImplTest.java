package semester3.project.sanomed.business.impl.PrescriptionService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.exceptions.InvalidPrescriptionException;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Person;
import semester3.project.sanomed.domain.Prescription;
import semester3.project.sanomed.domain.response.GetPrescriptionsResponse;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.persistence.Entity.PrescriptionEntity;
import semester3.project.sanomed.persistence.PrescriptionRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPrescriptionsByDiagnoseIdUseCaseImplTest {
    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private GetPrescriptionsByDiagnoseIdUseCaseImpl getPrescriptionsByDiagnoseIdUseCase;

    @Test
    void GetPrescriptionsByDiagnoseId(){
        PersonEmbeddable personEmbeddable = PersonEmbeddable.builder().firstName("Shar").lastName("Peeters").email("shar").phoneNumber("06868686").build();
        Person person = Person.builder().firstName("Shar").lastName("Peeters").email("shar").phoneNumber("06868686").build();
        EmployeeEntity employeeEntity = EmployeeEntity.builder().id(1L).personEmbeddable(personEmbeddable).build();
        Employee employee = Employee.builder().id(1L).person(person).build();
        DiagnoseEntity diagnoseEntity = DiagnoseEntity.builder().id(1L).build();

        PrescriptionEntity prescriptionEntity = PrescriptionEntity.builder().id(1L).diagnoseEntity(diagnoseEntity).doctor(employeeEntity).build();
        Prescription prescription= Prescription.builder().id(1L).doctor(employee).build();

        when(prescriptionRepository.findByDiagnoseEntity_Id(1L))
                .thenReturn(List.of(prescriptionEntity));

        GetPrescriptionsResponse actual = getPrescriptionsByDiagnoseIdUseCase.getPrescriptionsByDiagnoseId(1L);
        GetPrescriptionsResponse expected = GetPrescriptionsResponse.builder().prescriptions(List.of(prescription)).build();

        assertEquals(expected, actual);
        verify(prescriptionRepository).findByDiagnoseEntity_Id(1L);
    }

    @Test
    void getPrescription_shouldThrowException() throws InvalidPrescriptionException {
        when(prescriptionRepository.findByDiagnoseEntity_Id(1L))
                .thenReturn(Collections.emptyList());
        InvalidPrescriptionException thrown = assertThrows(InvalidPrescriptionException.class, () ->{
            getPrescriptionsByDiagnoseIdUseCase.getPrescriptionsByDiagnoseId(1L);
        }, "INVALID_PRESCRIPTION_ID");

        assertEquals("404 NOT_FOUND \"INVALID_PRESCRIPTION_ID\"", thrown.getMessage());
        verify(prescriptionRepository).findByDiagnoseEntity_Id(1L);
    }
}