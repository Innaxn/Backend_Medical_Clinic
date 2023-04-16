package semester3.project.sanomed.business.impl.Diagnose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import semester3.project.sanomed.business.converter.PersonConverter;
import semester3.project.sanomed.domain.Diagnose;
import semester3.project.sanomed.domain.Employee;
import semester3.project.sanomed.domain.Patient;
import semester3.project.sanomed.domain.Status_Employee;
import semester3.project.sanomed.persistence.DiagnoseRepository;
import semester3.project.sanomed.persistence.Entity.DiagnoseEntity;
import semester3.project.sanomed.persistence.Entity.EmployeeEntity;
import semester3.project.sanomed.persistence.Entity.PatientEntity;
import semester3.project.sanomed.persistence.Entity.PersonEmbeddable;
import semester3.project.sanomed.domain.response.GetDiagnosisResponse.DiagnoseData;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDiagnoseUseCaseImplTest {
    @Mock
    private DiagnoseRepository diagnoseRepositoryMock;
    @InjectMocks
    private GetDiagnoseUseCaseImpl getDiagnoseUseCaseImpl;


    @Test
    void getDiagnose_shouldEqualReturntrue() {
        PersonEmbeddable oneP = PersonEmbeddable.builder().firstName("Ivana").lastName("Nedelkova").phoneNumber("0898939914").birthdate(LocalDate.of(1980,10,1))
                .email( "nrdelkova@gmail.com").build();
        PatientEntity patient = PatientEntity.builder().id(1L).personEmbeddable(oneP).build();
        Patient p = Patient.builder().id(1L).person(PersonConverter.convert(oneP)).build();
        EmployeeEntity emp = EmployeeEntity.builder().id(1L).personEmbeddable(oneP).build();
        Employee e = Employee.builder().id(1L).person(PersonConverter.convert(oneP)).build();

        DiagnoseEntity diagnoseOne = DiagnoseEntity.builder().id(1L).patient(patient).doctor(emp).name("test").details("test").build();

        when(diagnoseRepositoryMock.findById(1L))
                .thenReturn(Optional.of(diagnoseOne));

        Optional<DiagnoseData> actualResult = getDiagnoseUseCaseImpl.getDiagnose(1L);

        DiagnoseData expected = DiagnoseData.builder().id(1L).FirstNamePatient("Ivana").FirstNameDoctor("Ivana").LastNameDoctor("Nedelkova").LastNamePatient("Nedelkova")
                .dId(1L).pId(1L)
                .name("test").details("test")
                .build();

        assertEquals(expected, actualResult.get());
        verify(diagnoseRepositoryMock).findById(1L);
    }
}